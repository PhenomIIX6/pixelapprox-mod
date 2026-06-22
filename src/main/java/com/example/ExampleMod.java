package com.example;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

// getString(ctx, "string")
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
// word()
import static com.mojang.brigadier.arguments.StringArgumentType.word;
// literal("foo")
import static net.minecraft.commands.Commands.literal;

// argument("bar", word())
import static net.minecraft.commands.Commands.argument;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register
		(
			(dispatcher, registryAccess, environment) -> 
			{
				dispatcher.register
				(
					literal("pixelapprox")
					.then
					(
						argument("path", StringArgumentType.string())
						.then
						(
							argument("position", BlockPosArgument.blockPos())
							.executes(context ->
								executePixelApprox(
									StringArgumentType.getString(context, "path"),
									BlockPosArgument.getBlockPos(context, "position"),
									context.getSource().getLevel()
								)
							)
							.then
							(
								argument("width", IntegerArgumentType.integer())
								.then
								(
									argument("height", IntegerArgumentType.integer())
									.executes(context -> executePixelApprox
										(
											StringArgumentType.getString(context, "path"),
											BlockPosArgument.getBlockPos(context, "position"),
											context.getSource().getLevel(),
											IntegerArgumentType.getInteger(context, "width"),
											IntegerArgumentType.getInteger(context, "height")
										)
									)
								)
							)
						)
					)
				);
			}
		);
	}

	private static int blocksPlace(ServerLevel level, Block[][] blocks, BlockPos position, int width, int height) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int curX = position.getX() + width - 1 - x;
				int curY = position.getY() + height - 1 - y;
				int curZ = position.getZ();

				BlockPos cur_pos = new BlockPos(curX, curY, curZ);
				level.setBlock(cur_pos, blocks[x][y].defaultBlockState(), 3);
			}
		}
		return 1;
	}

	private static int executePixelApprox(String path, BlockPos position, ServerLevel level) {
		return executePixelApprox(path, position, level, -1, -1);
	}

	private static int executePixelApprox(String path, BlockPos position, ServerLevel level, int width, int height) {
		// Load image
		BufferedImage img;
		try {
			img = ImageIO.read(new File(path));
		}
		catch (IOException e) {
			LOGGER.info(e.getMessage());
			return -1;
		}
		
		// Scale image
		if (width > 0 && height > 0) {
			img = ImageUtils.scaleImage(img, width, height);
		}
		
		// Get array of blocks
		Block[][] blocks = PixelApproximator.imageToBlocks(img);
		
		// Blocks place
		int w = img.getWidth();
		int h = img.getHeight();
		return blocksPlace(level, blocks, position, w, h);
	}
}
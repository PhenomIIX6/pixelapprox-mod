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
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.Commands.argument;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Runs a /pixelapprox invocation. `facing` is null when the direction was not
	// given explicitly (or the "player" keyword was used), and is then resolved
	// from the player's look direction.
	@FunctionalInterface
	private interface PixelApproxRunner {
		int run(CommandContext<CommandSourceStack> ctx, Direction facing);
	}

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register
		(
			(dispatcher, registryAccess, environment) -> 
			{
				// /pixelapprox <path> <position> [facing]
				ArgumentBuilder<CommandSourceStack, ?> positionNode = argument("position", BlockPosArgument.blockPos());
				positionNode.executes(ctx -> executePixelApprox(ctx, null, -1, -1));
				addFacingOptions(positionNode, (ctx, facing) -> executePixelApprox(ctx, facing, -1, -1));

				// /pixelapprox <path> <position> <width> <height> [facing]
				ArgumentBuilder<CommandSourceStack, ?> heightNode = argument("height", IntegerArgumentType.integer());
				heightNode.executes(ctx -> executePixelApprox(
					ctx, null,
					IntegerArgumentType.getInteger(ctx, "width"),
					IntegerArgumentType.getInteger(ctx, "height")));
				addFacingOptions(heightNode, (ctx, facing) -> executePixelApprox(
					ctx, facing,
					IntegerArgumentType.getInteger(ctx, "width"),
					IntegerArgumentType.getInteger(ctx, "height")));

				ArgumentBuilder<CommandSourceStack, ?> widthNode = argument("width", IntegerArgumentType.integer());
				widthNode.then(heightNode);
				positionNode.then(widthNode);

				dispatcher.register
				(
					literal("pixelapprox")
					.then
					(
						argument("path", StringArgumentType.string())
						.then(positionNode)
					)
				);
			}
		);
	}

	// Adds `north|south|east|west|player` as the trailing direction argument.
	// Literals are used (instead of a word argument) so they never clash with the
	// integer width/height arguments and gain tab completion for free.
	private static void addFacingOptions(ArgumentBuilder<CommandSourceStack, ?> node, PixelApproxRunner runner) {
		for (Direction dir : new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST }) {
			node.then(literal(dir.getName()).executes(ctx -> runner.run(ctx, dir)));
		}
		node.then(literal("player").executes(ctx -> runner.run(ctx, null)));
	}

	// Resolves the effective picture facing. An explicit direction wins; otherwise
	// (keyword "player" or no direction given) the picture is turned to face the
	// player who ran the command. 
	private static Direction resolveFacing(CommandSourceStack source, Direction facing) {
		if (facing != null) {
			return facing;
		}
		ServerPlayer player = source.getPlayer();
        return player.getDirection().getOpposite();
	}

	private static int executePixelApprox(CommandContext<CommandSourceStack> ctx, Direction facing, int width, int height) {
		return executePixelApprox(
			getString(ctx, "path"),
			BlockPosArgument.getBlockPos(ctx, "position"),
			ctx.getSource().getLevel(),
			width,
			height,
			resolveFacing(ctx.getSource(), facing)
		);
	}

	private static int blocksPlace(ServerLevel level, Block[][] blocks, BlockPos position, int width, int height, Direction facing) {
		int rightX = 0;
		int rightZ = 0;
		switch (facing) {
			case SOUTH -> { rightX = 1; rightZ = 0; }
			case NORTH -> { rightX = -1; rightZ = 0; }
			case EAST  -> { rightX = 0; rightZ = -1; }
			case WEST  -> { rightX = 0; rightZ = 1; }
			default    -> { rightX = 1; rightZ = 0; }
		}

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int offs = width - 1 - x;
				int curX = position.getX() + rightX * offs;
				int curY = position.getY() + height - 1 - y;
				int curZ = position.getZ() + rightZ * offs;

				BlockPos cur_pos = new BlockPos(curX, curY, curZ);
				level.setBlock(cur_pos, blocks[x][y].defaultBlockState(), 3);
			}
		}
		return 1;
	}

	private static int executePixelApprox(String path, BlockPos position, ServerLevel level, int width, int height, Direction facing) {
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
		return blocksPlace(level, blocks, position, w, h, facing);
	}
}

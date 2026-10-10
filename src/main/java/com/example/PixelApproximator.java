package com.example;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;


public class PixelApproximator {	
    private static final ArrayList<MyBlock> myBlocks = new ArrayList<MyBlock>(Arrays.asList(
        MyBlocks.ACACIA_PLANKS,
        MyBlocks.BAMBOO_PLANKS,
        MyBlocks.BIRCH_PLANKS,
        MyBlocks.BLACK_CONCRETE,
        MyBlocks.BLACK_CONCRETE_POWDER,
        MyBlocks.BLACK_TERRACOTTA,
        MyBlocks.BLACK_WOOL,
        MyBlocks.BLUE_CONCRETE,
        MyBlocks.BLUE_CONCRETE_POWDER,
        MyBlocks.BLUE_TERRACOTTA,
        MyBlocks.BLUE_WOOL,
        MyBlocks.BROWN_CONCRETE,
        MyBlocks.BROWN_CONCRETE_POWDER,
        MyBlocks.BROWN_TERRACOTTA,
        MyBlocks.BROWN_WOOL,
        MyBlocks.CHERRY_PLANKS,
        MyBlocks.CRIMSON_PLANKS,
        MyBlocks.CYAN_CONCRETE,
        MyBlocks.CYAN_CONCRETE_POWDER,
        MyBlocks.CYAN_TERRACOTTA,
        MyBlocks.CYAN_WOOL,
        MyBlocks.DARK_OAK_PLANKS,
        MyBlocks.GRAY_CONCRETE,
        MyBlocks.GRAY_CONCRETE_POWDER,
        MyBlocks.GRAY_TERRACOTTA,
        MyBlocks.GRAY_WOOL,
        MyBlocks.GREEN_CONCRETE,
        MyBlocks.GREEN_CONCRETE_POWDER,
        MyBlocks.GREEN_TERRACOTTA,
        MyBlocks.GREEN_WOOL,
        MyBlocks.JUNGLE_PLANKS,
        MyBlocks.LIGHT_BLUE_CONCRETE,
        MyBlocks.LIGHT_BLUE_CONCRETE_POWDER,
        MyBlocks.LIGHT_BLUE_TERRACOTTA,
        MyBlocks.LIGHT_BLUE_WOOL,
        MyBlocks.LIGHT_GRAY_CONCRETE,
        MyBlocks.LIGHT_GRAY_CONCRETE_POWDER,
        MyBlocks.LIGHT_GRAY_TERRACOTTA,
        MyBlocks.LIGHT_GRAY_WOOL,
        MyBlocks.LIME_CONCRETE,
        MyBlocks.LIME_CONCRETE_POWDER,
        MyBlocks.LIME_TERRACOTTA,
        MyBlocks.LIME_WOOL,
        MyBlocks.MAGENTA_CONCRETE,
        MyBlocks.MAGENTA_CONCRETE_POWDER,
        MyBlocks.MAGENTA_TERRACOTTA,
        MyBlocks.MAGENTA_WOOL,
        MyBlocks.MANGROVE_PLANKS,
        MyBlocks.OAK_PLANKS,
        MyBlocks.ORANGE_CONCRETE,
        MyBlocks.ORANGE_CONCRETE_POWDER,
        MyBlocks.ORANGE_TERRACOTTA,
        MyBlocks.ORANGE_WOOL,
        MyBlocks.PALE_OAK_PLANKS,
        MyBlocks.PINK_CONCRETE,
        MyBlocks.PINK_CONCRETE_POWDER,
        MyBlocks.PINK_TERRACOTTA,
        MyBlocks.PINK_WOOL,
        MyBlocks.POLISHED_ANDESITE,
        MyBlocks.POLISHED_BLACKSTONE,
        MyBlocks.POLISHED_DIORITE,
        MyBlocks.POLISHED_GRANITE,
        MyBlocks.POLISHED_TUFF,
        MyBlocks.PURPLE_CONCRETE,
        MyBlocks.PURPLE_CONCRETE_POWDER,
        MyBlocks.PURPLE_TERRACOTTA,
        MyBlocks.PURPLE_WOOL,
        MyBlocks.RED_CONCRETE,
        MyBlocks.RED_CONCRETE_POWDER,
        MyBlocks.RED_TERRACOTTA,
        MyBlocks.RED_WOOL,
        MyBlocks.SPRUCE_PLANKS,
        MyBlocks.TERRACOTTA,
        MyBlocks.WARPED_PLANKS,
        MyBlocks.WHITE_CONCRETE,
        MyBlocks.WHITE_CONCRETE_POWDER,
        MyBlocks.WHITE_TERRACOTTA,
        MyBlocks.WHITE_WOOL,
        MyBlocks.YELLOW_CONCRETE,
        MyBlocks.YELLOW_CONCRETE_POWDER,
        MyBlocks.YELLOW_TERRACOTTA,
        MyBlocks.YELLOW_WOOL
    ));

    public static Block[][] imageToBlocks(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        
        Block[][] blocks = new Block[w][h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                C3 color = ImageUtils.getPixelColor(img, x, y);
                blocks[x][y] = ClosestFinder.findClosestMyBlock(color, myBlocks).getBlock();
            }
        }
        return blocks;
    }

    public Block[][] imageToBlocks(String imgPath) throws IOException {
        BufferedImage img = ImageIO.read(new File(imgPath));
        return imageToBlocks(img);
    }
}
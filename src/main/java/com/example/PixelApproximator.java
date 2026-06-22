package com.example;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

import net.minecraft.world.level.block.Block;

public class PixelApproximator {	
    private static final ArrayList<MyBlock> myBlocks = new ArrayList<MyBlock>(Arrays.asList(
        MyBlocks.BLACK_CONCRETE,
        MyBlocks.BLACK_CONCRETE_POWDER,
        MyBlocks.BLACK_TERRACOTTA,
        MyBlocks.BLUE_CONCRETE,
        MyBlocks.BLUE_CONCRETE_POWDER,
        MyBlocks.BLUE_TERRACOTTA,
        MyBlocks.BROWN_CONCRETE,
        MyBlocks.BROWN_CONCRETE_POWDER,
        MyBlocks.BROWN_TERRACOTTA,
        MyBlocks.CYAN_CONCRETE,
        MyBlocks.CYAN_CONCRETE_POWDER,
        MyBlocks.CYAN_TERRACOTTA,
        MyBlocks.GRAY_CONCRETE,
        MyBlocks.GRAY_CONCRETE_POWDER,
        MyBlocks.GRAY_TERRACOTTA,
        MyBlocks.GREEN_CONCRETE,
        MyBlocks.GREEN_CONCRETE_POWDER,
        MyBlocks.GREEN_TERRACOTTA,
        MyBlocks.LIGHT_BLUE_CONCRETE,
        MyBlocks.LIGHT_BLUE_CONCRETE_POWDER,
        MyBlocks.LIGHT_BLUE_TERRACOTTA,
        MyBlocks.LIGHT_GRAY_CONCRETE,
        MyBlocks.LIGHT_GRAY_CONCRETE_POWDER,
        MyBlocks.LIGHT_GRAY_TERRACOTTA,
        MyBlocks.LIME_CONCRETE,
        MyBlocks.LIME_CONCRETE_POWDER,
        MyBlocks.LIME_TERRACOTTA,
        MyBlocks.MAGENTA_CONCRETE,
        MyBlocks.MAGENTA_CONCRETE_POWDER,
        MyBlocks.MAGENTA_TERRACOTTA,
        MyBlocks.ORANGE_CONCRETE,
        MyBlocks.ORANGE_CONCRETE_POWDER,
        MyBlocks.ORANGE_TERRACOTTA,
        MyBlocks.PINK_CONCRETE,
        MyBlocks.PINK_CONCRETE_POWDER,
        MyBlocks.PINK_TERRACOTTA,
        MyBlocks.PURPLE_CONCRETE,
        MyBlocks.PURPLE_CONCRETE_POWDER,
        MyBlocks.PURPLE_TERRACOTTA,
        MyBlocks.RED_CONCRETE,
        MyBlocks.RED_CONCRETE_POWDER,
        MyBlocks.RED_TERRACOTTA,
        MyBlocks.TERRACOTTA,
        MyBlocks.WHITE_CONCRETE,
        MyBlocks.WHITE_CONCRETE_POWDER,
        MyBlocks.WHITE_TERRACOTTA,
        MyBlocks.YELLOW_CONCRETE,
        MyBlocks.YELLOW_CONCRETE_POWDER,
        MyBlocks.YELLOW_TERRACOTTA
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

    public static Block[][] imageToBlocks(String imgPath) throws IOException {
        BufferedImage img = ImageIO.read(new File(imgPath));
        return imageToBlocks(img);
    }
}
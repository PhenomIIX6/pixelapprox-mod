package com.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Dither {
    private C3[] palette;
    private Map<Color, Integer> paletteColorToIdx = new HashMap<>();

    public Dither(C3[] palette) {
        for (int i = 0; i < palette.length; i++) {
            paletteColorToIdx.put(palette[i].toColor(), i);
        }
        this.palette = palette;
    }

    private static C3 findClosestPaletteColor(C3 c, C3[] palette) {
        C3 closest = palette[0];

        for (C3 n : palette) {
            if (n.diff(c) < closest.diff(c)) {
                closest = n;
            }
        }

        return closest;
    }

    public int[][] floydSteinbergDithering(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        C3[][] d = new C3[h][w];
        int[][] idxs = new int[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                d[y][x] = new C3(img.getRGB(x, y));
            }
        }

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                C3 oldColor = d[y][x];
                C3 newColor = findClosestPaletteColor(oldColor, palette);
                
                idxs[y][x] = paletteColorToIdx.get(newColor.toColor());

        //         C3 err = oldColor.sub(newColor);

        //         if (x + 1 < w) {
        //             d[y][x + 1] = d[y][x + 1].add(err.mul(7. / 16));
        //         }
                
        //         if (x - 1 >= 0 && y + 1 < h) {
        //             d[y + 1][x - 1] = d[y + 1][x - 1].add(err.mul(3. / 16));
        //         }
                
        //         if (y + 1 < h) {
        //             d[y + 1][x] = d[y + 1][x].add(err.mul(5. / 16));
        //         }
                
        //         if (x + 1 < w && y + 1 < h) {
        //             d[y + 1][x + 1] = d[y + 1][x + 1].add(err.mul(1. / 16));
        //         }
            }
        }

        return idxs;
    }

}
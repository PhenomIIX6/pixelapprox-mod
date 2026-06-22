package com.example;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageUtils {
    
    public static C3 getPixelColor(BufferedImage img, int x, int y) {
        int color = img.getRGB(x, y);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        
        return new C3(red, green, blue);
    }
    
    public static BufferedImage scaleImage(BufferedImage img, int dWidth, int dHeight) {
        BufferedImage scaledImg = new BufferedImage(dWidth, dHeight, img.getType());

        Graphics2D graphics2D = scaledImg.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics2D.drawImage(img, 0, 0, dWidth, dHeight, null);
        graphics2D.dispose();
    
        return scaledImg;
    }

}

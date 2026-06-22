package com.example;

public class RGBToLabConverter {
    private static final double D65_X = 95.047;
    private static final double D65_Y = 100.0;
    private static final double D65_Z = 108.883;

    public static C3 rgbToLab(int red, int green, int blue) {
        double[] rgb = {red / 255.0, green / 255.0, blue / 255.0};

        double[] linearRgb = new double[3];
        for (int i = 0; i < 3; i++) {
            linearRgb[i] = rgb[i] <= 0.04045 ?
                    rgb[i] / 12.92 :
                    Math.pow((rgb[i] + 0.055) / 1.055, 2.4);
        }

        double x = linearRgb[0] * 0.4124564 + linearRgb[1] * 0.3575761 + linearRgb[2] * 0.1804375;
        double y = linearRgb[0] * 0.2126729 + linearRgb[1] * 0.7151522 + linearRgb[2] * 0.0721750;
        double z = linearRgb[0] * 0.0193339 + linearRgb[1] * 0.1191920 + linearRgb[2] * 0.9503041;

        x /= D65_X;
        y /= D65_Y;
        z /= D65_Z;

        double fx = xyzF(x);
        double fy = xyzF(y);
        double fz = xyzF(z);

        double L = 116.0 * fy - 16;
        double a = 500.0 * (fx - fy);
        double b = 200.0 * (fy - fz);

        return new C3(L, a, b);
    }

    private static double xyzF(double t) {
        return t > 0.008856 ?
                Math.cbrt(t) :
                (7.787 * t) + (16.0 / 116.0);
    }
}

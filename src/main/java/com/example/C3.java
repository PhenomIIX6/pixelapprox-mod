package com.example;

import java.awt.Color;

public class C3 {
    int r, g, b;

    public C3(int c) {
        Color color = new Color(c);
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    public C3(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public C3 add(C3 o) {
        return new C3(r + o.r, g + o.g, b + o.b);
    }

    public int clamp(int c) {
        return Math.max(0, Math.min(255, c));
    }

    public int diff(C3 o) {
        int Rdiff = o.r - r;
        int Gdiff = o.g - g;
        int Bdiff = o.b - b;
        int distanceSquared = Rdiff * Rdiff + Gdiff * Gdiff + Bdiff * Bdiff;
        return distanceSquared;
    }

    public C3 mul(double d) {
        return new C3((int) Math.round(d * r), (int) Math.round(d * g), (int) Math.round(d * b));
    }

    public C3 div(double d) {
        return new C3((int) Math.round(r / d), (int) Math.round(g / d), (int) Math.round(b / d));
    }

    public C3 sub(C3 o) {
        return new C3(r - o.r, g - o.g, b - o.b);
    }

    public Color toColor() {
        return new Color(clamp(r), clamp(g), clamp(b));
    }

    public int toRGB() {
        return toColor().getRGB();
    }
}
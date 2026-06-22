package com.example;

public class C3 {
    double l, a, b;

    public C3(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public double getL() {
        return this.l;
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public double diff(C3 o) {
        double Rdiff = o.l - l;
        double Gdiff = o.a - a;
        double Bdiff = o.b - b;
        double distanceSquared = Rdiff * Rdiff + Gdiff * Gdiff + Bdiff * Bdiff;
        return distanceSquared;
    }

    public C3 mul(double d) {
        return new C3((double) Math.round(d * l), (double) Math.round(d * a), (double) Math.round(d * b));
    }

    public C3 div(double d) {
        return new C3((double) Math.round(l / d), (double) Math.round(a / d), (double) Math.round(b / d));
    }

    public C3 add(C3 o) {
        return new C3(l + o.l, a + o.a, b + o.b);
    }

    public C3 sub(C3 o) {
        return new C3(l - o.l, a - o.a, b - o.b);
    }

}
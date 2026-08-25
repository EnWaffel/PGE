package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Color {

    public float r = 255.0f;
    public float g = 255.0f;
    public float b = 255.0f;
    public float a = 255.0f;

    public Color() {}

    public Color(float gray) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
    }

    public Color(float gray, float alpha) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
        this.a = alpha;
    }

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int rgb) {
        this.r = (rgb >> 16) & 0xFF;
        this.g = (rgb >> 8) & 0xFF;
        this.b = rgb & 0xFF;
    }

    public Color(String hex) {
        if (hex.startsWith("#")) hex = hex.substring(1);

        if (hex.length() == 3 || hex.length() == 4) {
            StringBuilder expanded = new StringBuilder();
            for (char c : hex.toCharArray()) {
                expanded.append(c).append(c);
            }
            hex = expanded.toString();
        }

        if (hex.length() != 6 && hex.length() != 8) {
            throw new IllegalArgumentException("Invalid hex color: #" + hex);
        }

        this.r = Integer.parseInt(hex.substring(0, 2), 16);
        this.g = Integer.parseInt(hex.substring(2, 4), 16);
        this.b = Integer.parseInt(hex.substring(4, 6), 16);
        this.a = hex.length() == 8 ? Integer.parseInt(hex.substring(6, 8), 16) : 255f;
    }

    public static Color fromHSB(float h, float s, float v, float alpha) {
        float r, g, b;

        if (s <= 0f) {
            r = g = b = v;
        } else {
            float hh = (h - PApplet.floor(h)) * 6f;
            int i = (int) hh;
            float f = hh - i;
            float p = v * (1f - s);
            float q = v * (1f - s * f);
            float t = v * (1f - s * (1f - f));

            switch (i) {
                case 0: r = v; g = t; b = p; break;
                case 1: r = q; g = v; b = p; break;
                case 2: r = p; g = v; b = t; break;
                case 3: r = p; g = q; b = v; break;
                case 4: r = t; g = p; b = v; break;
                default: r = v; g = p; b = q; break;
            }
        }

        return new Color(r * 255f, g * 255f, b * 255f, alpha);
    }

    public static Color fromHSB(float h, float s, float v) {
        return fromHSB(h, s, v, 255f);
    }

    public void apply(PGraphics pg) {
        pg.fill(r, g, b, a);
    }

    public Color lerp(Color other, float t) {
        return new Color(
                PApplet.lerp(r, other.r, t),
                PApplet.lerp(g, other.g, t),
                PApplet.lerp(b, other.b, t),
                PApplet.lerp(a, other.a, t)
        );
    }

    public int toARGB() {
        return ((int) a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
    }

    @Override
    public String toString() {
        return String.format("#%02X%02X%02X%02X", (int) r, (int) g, (int) b, (int) a);
    }

}
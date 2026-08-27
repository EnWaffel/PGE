package de.enwaffel.pge;

public abstract class ColorConstants {

    public Color _color(float gray) {
        return new Color(gray);
    }

    public Color _color(float gray, float alpha) {
        return new Color(gray, alpha);
    }

    public Color _color(float r, float g, float b) {
        return new Color(r, g, b);
    }

    public Color _color(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    public Color _color(int rgb) {
        return new Color(rgb);
    }

    public Color _color(String hex) {
        return new Color(hex);
    }

}

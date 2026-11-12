package de.enwaffel.pge;

public final class MathUtils {

    private MathUtils() {}

    public static boolean isIntersection(float x, float y, float w, float h,
                                         float x1, float y1, float w1, float h1) {
        if (w <= 0 || h <= 0 || w1 <= 0 || h1 <= 0) return false;

        return x < x1 + w1 && x + w > x1
                && y < y1 + h1 && y + h > y1;
    }

}

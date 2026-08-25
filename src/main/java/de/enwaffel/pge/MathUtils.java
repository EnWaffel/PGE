package de.enwaffel.pge;

public final class MathUtils {

    private MathUtils() {}

    public static boolean isIntersection(float x, float y, float w, float h, float x1, float y1, float w1, float h1) {
        float tw = w;
        float th = h;
        float rw = w1;
        float rh = h1;

        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }

        rw += x1;
        rh += y1;
        tw += x;
        th += y;

        return ((rw < x1 || rw > x) &&
                (rh < y1 || rh > y) &&
                (tw < x || tw > x1) &&
                (th < y || th > y1));
    }

}

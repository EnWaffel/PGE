package de.enwaffel.pge;

public record Rect(float x, float y, float w, float h) {

    public boolean intersects(Rect o) {
        return MathUtils.isIntersection(x, y, w, h, o.x, o.y, o.w, o.h);
    }

}

package de.enwaffel.pge;

import processing.core.PGraphics;

public record DrawContext(PGraphics pg, float delta) {

    public DrawContext copyWithNewGraphics(PGraphics pg) {
        return new DrawContext(pg, delta);
    }

}

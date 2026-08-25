package de.enwaffel.pge;

import processing.core.PSurface;
import processing.opengl.PGraphics3D;

public class PGERenderer extends PGraphics3D {

    @Override
    public PSurface createSurface() {
        return new PGEPSurfaceJOGL(this);
    }

}

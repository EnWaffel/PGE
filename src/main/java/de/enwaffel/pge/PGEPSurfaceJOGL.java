package de.enwaffel.pge;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import processing.core.PGraphics;
import processing.opengl.PSurfaceJOGL;

public class PGEPSurfaceJOGL extends PSurfaceJOGL {

    public PGEPSurfaceJOGL(PGraphics graphics) {
        super(graphics);
    }

    @Override
    protected void initGL() {
        graphics.smooth = 8;
        super.initGL();

        GLCapabilitiesImmutable current = pgl.getCaps();
        GLCapabilities caps = (GLCapabilities) current;

        caps.setBackgroundOpaque(false);

        pgl.setCaps(caps);
    }

}
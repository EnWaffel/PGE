package de.enwaffel.pge.particle.particles;

import de.enwaffel.pge.Color;
import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.ParticleFactory;
import de.enwaffel.pge.particle.ParticleTemplate;
import de.enwaffel.pge.utils.Properties;
import processing.core.PGraphics;
import processing.core.PVector;

public class SoftCircleParticle extends SimpleParticle {

    public float radius;

    private SoftCircleParticle(ParticleTemplate template, Properties properties) {
        super(template, properties);

        this.radius = properties.get("radius", Float.class, 0f);
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();
        Color c = getCurrentColor();

        pg.pushMatrix();
        applyTransforms(pg, new PVector(radius, radius));
        applyEffects(pg);

        int steps = 8;
        for (int i = steps; i > 0; i--) {
            float t = (float) i / steps;
            float r = radius * t;
            float alpha = c.a * (1f - t) * (1f - t);
            pg.noStroke();
            pg.fill(c.r, c.g, c.b, alpha);
            pg.ellipse(0, 0, r * 2, r * 2);
        }

        pg.popMatrix();
    }

    public static final class Factory implements ParticleFactory {

        @Override
        public Particle create(ParticleTemplate template, Properties properties) {
            return new SoftCircleParticle(
                    template,
                    properties
            );
        }

    }

}

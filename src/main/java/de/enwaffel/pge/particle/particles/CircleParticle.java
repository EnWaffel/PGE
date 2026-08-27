package de.enwaffel.pge.particle.particles;

import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.ParticleFactory;
import de.enwaffel.pge.particle.ParticleTemplate;
import de.enwaffel.pge.utils.Properties;
import processing.core.PGraphics;
import processing.core.PVector;

public class CircleParticle extends SimpleParticle {

    public float radius;

    private CircleParticle(ParticleTemplate template, Properties properties) {
        super(template, properties);

        this.radius = properties.get("radius", Float.class, 0f);
    }

    @Override
    public void draw(DrawContext ctx) {
        super.draw(ctx);
        PGraphics pg = ctx.pg();

        pg.push();

        applyTransforms(pg, new PVector(radius, radius));
        applyColor(pg);
        applyEffects(pg);

        pg.noStroke();
        pg.circle(0, 0, radius);

        pg.pop();
    }

    public static final class Factory implements ParticleFactory {

        @Override
        public Particle create(ParticleTemplate template, Properties properties) {
            return new CircleParticle(
                    template,
                    properties
            );
        }

    }

}

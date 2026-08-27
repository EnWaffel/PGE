package de.enwaffel.pge.particle.particles;

import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.ParticleFactory;
import de.enwaffel.pge.particle.ParticleTemplate;
import de.enwaffel.pge.utils.Properties;
import processing.core.PGraphics;
import processing.core.PVector;

public class RectParticle extends SimpleParticle {

    public PVector size;

    private RectParticle(ParticleTemplate template, Properties properties) {
        super(template, properties);

        this.size = new PVector(
                properties.get("width", Float.class, 0f),
                properties.get("height", Float.class, 0f)
        );
    }

    @Override
    public void draw(DrawContext ctx) {
        super.draw(ctx);
        PGraphics pg = ctx.pg();

        pg.push();

        applyTransforms(pg, size);
        applyColor(pg);
        applyEffects(pg);

        pg.noStroke();
        pg.rect(0, 0, size.x, size.y);

        pg.pop();
    }

    public static final class Factory implements ParticleFactory {

        @Override
        public Particle create(ParticleTemplate template, Properties properties) {
            return new RectParticle(
                    template,
                    properties
            );
        }

    }

}

package de.enwaffel.pge.particle.particles;

import de.enwaffel.pge.Color;
import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.ParticleFactory;
import de.enwaffel.pge.particle.ParticleTemplate;
import de.enwaffel.pge.utils.Properties;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class FlameParticle extends SimpleParticle {

    public PVector size;

    private FlameParticle(ParticleTemplate template, Properties properties) {
        super(template, properties);

        this.size = new PVector(
                properties.get("width", Float.class, 0f),
                properties.get("height", Float.class, 0f)
        );
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();
        Color c = getCurrentColor();
        float angle = vel.heading() + PApplet.HALF_PI;

        pg.pushMatrix();
        pg.translate(pos.x, pos.y);
        pg.rotate(angle);
        applyEffects(pg);
        pg.fill(c.r, c.g, c.b, c.a);
        pg.noStroke();
        pg.beginShape();
        pg.vertex(0, -size.y);
        pg.vertex(-size.x * 0.5f, size.y);
        pg.vertex(size.x * 0.5f, size.y);
        pg.endShape(PApplet.CLOSE);
        pg.popMatrix();
    }

    public static final class Factory implements ParticleFactory {

        @Override
        public Particle create(ParticleTemplate template, Properties properties) {
            return new FlameParticle(
                    template,
                    properties
            );
        }

    }

}

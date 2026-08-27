package de.enwaffel.pge.particle;

import de.enwaffel.pge.Drawable;
import de.enwaffel.pge.Game;
import processing.core.PVector;

public abstract class Particle implements Drawable {

    public PVector origin = Game.ORIGIN;
    public final ParticleTemplate template;

    public PVector pos = new PVector();
    public PVector vel = new PVector();
    public PVector acc = new PVector();

    public float rotation = 0f;
    public float angularVel = 0f;

    public float lifetime = 0f;
    public float maxLifetime = 0f;

    public float alpha = 1f;

    protected Particle(ParticleTemplate template) {
        this.template = template;
    }

    public boolean isDead() {
        return lifetime <= 0f;
    }

    public float normalizedLifetime() {
        return 1f - (lifetime / maxLifetime);
    }

}

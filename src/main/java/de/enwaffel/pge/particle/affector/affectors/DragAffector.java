package de.enwaffel.pge.particle.affector.affectors;

import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.affector.ParticleAffector;

public class DragAffector implements ParticleAffector {

    private final float drag;

    public DragAffector(float gravity) {
        this.drag = gravity;
    }

    @Override
    public void apply(Particle particle, float delta) {
        particle.vel.mult(1 - drag * delta);
    }

}

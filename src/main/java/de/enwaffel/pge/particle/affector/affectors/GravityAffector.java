package de.enwaffel.pge.particle.affector.affectors;

import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.affector.ParticleAffector;

public class GravityAffector implements ParticleAffector {

    private final float gravity;

    public GravityAffector(float gravity) {
        this.gravity = gravity;
    }

    @Override
    public void apply(Particle particle, float delta) {
        particle.acc.y += gravity * delta;
    }

}

package de.enwaffel.pge.particle.affector;

import de.enwaffel.pge.particle.Particle;

public interface ParticleAffector {
    void apply(Particle particle, float delta);
}

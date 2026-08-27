package de.enwaffel.pge.particle.affector.affectors;

import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.affector.ParticleAffector;

public class FadeAffector implements ParticleAffector {

    @Override
    public void apply(Particle particle, float delta) {
        particle.alpha = 1f - particle.normalizedLifetime();
    }

}

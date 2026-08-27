package de.enwaffel.pge.particle;

import de.enwaffel.pge.utils.Properties;

public interface ParticleFactory {
    Particle create(ParticleTemplate template, Properties properties);
}

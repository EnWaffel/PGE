package de.enwaffel.pge.particle;

import de.enwaffel.pge.Color;
import de.enwaffel.pge.Game;
import de.enwaffel.pge.particle.affector.ParticleAffector;
import de.enwaffel.pge.particle.affector.affectors.*;
import de.enwaffel.pge.particle.particles.*;
import de.enwaffel.pge.utils.Properties;
import de.enwaffel.pge.utils.Range;

import java.util.*;

public class ParticleTemplate {

    private static final Map<String, ParticleTemplate> TEMPLATES = new HashMap<>();

    private final String name;
    private final ParticleFactory factory;
    final List<ParticleAffector> affectors;

    private final Range<Float> offsetX;
    private final Range<Float> offsetY;
    private final Range<Float> velocityX;
    private final Range<Float> velocityY;
    private final Range<Float> angularVelocity;
    private final Range<Float> lifetime;
    private final Properties properties;

    private ParticleTemplate(
            String name,
            ParticleFactory factory,
            List<ParticleAffector> affectors,
            Range<Float> offsetX,
            Range<Float> offsetY,
            Range<Float> velocityX,
            Range<Float> velocityY,
            Range<Float> angularVelocity,
            Properties properties,
            Range<Float> lifetime
    ) {
        this.name = name;
        this.factory = factory;
        this.affectors = affectors;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.angularVelocity = angularVelocity;
        this.properties = properties;
        this.lifetime = lifetime;
    }

    public Particle makeParticle() {
        Particle particle = factory.create(this, properties);

        float maxLifetime = Game.randomFloat(lifetime.min(), lifetime.max());
        particle.lifetime = maxLifetime;
        particle.maxLifetime = maxLifetime;

        if (!offsetX.isZero()) {
            particle.pos.x = Game.randomFloat(offsetX.min(), offsetX.max());
        }
        if (!offsetY.isZero()) {
            particle.pos.y = Game.randomFloat(offsetY.min(), offsetY.max());
        }
        if (!velocityX.isZero()) {
            particle.vel.x = Game.randomFloat(velocityX.min(), velocityX.max());
        }
        if (!velocityY.isZero()) {
            particle.vel.y = Game.randomFloat(velocityY.min(), velocityY.max());
        }
        if (!angularVelocity.isZero()) {
            particle.angularVel = Game.randomFloat(angularVelocity.min(), angularVelocity.max());
        }

        return particle;
    }

    public void stepParticle(Particle particle, float delta) {
        particle.lifetime -= delta;

        for (ParticleAffector affector : affectors) {
            affector.apply(particle, delta);
        }

        particle.rotation += particle.angularVel * delta;
        particle.vel.add(particle.acc.copy().mult(delta));
        particle.pos.add(particle.vel.copy().mult(delta));
        particle.acc.set(0, 0);
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder(String name) {
        return new Builder(name);
    }

    public static ParticleTemplate get(String name) {
        if (!TEMPLATES.containsKey(name)) return null;
        return TEMPLATES.get(name);
    }

    public static class Builder {

        private final String name;
        private final List<ParticleAffector> affectors = new ArrayList<>();
        private final Properties properties = new Properties();

        private ParticleFactory factory = new RectParticle.Factory();
        private Range<Float> offsetX = new Range<>(0f, 0f);
        private Range<Float> offsetY = new Range<>(0f, 0f);
        private Range<Float> velocityX = new Range<>(0f, 0f);
        private Range<Float> velocityY = new Range<>(0f, 0f);
        private Range<Float> angularVelocity = new Range<>(0f, 0f);
        private Range<Float> lifetime = new Range<>(1f, 1f);

        public Builder(String name) {
            this.name = name;
        }

        public Builder rect() {
            factory = new RectParticle.Factory();
            return this;
        }

        public Builder circle() {
            factory = new CircleParticle.Factory();
            return this;
        }

        public Builder softCircle() {
            factory = new SoftCircleParticle.Factory();
            return this;
        }

        public Builder streak() {
            factory = new StreakParticle.Factory();
            return this;
        }

        public Builder flame() {
            factory = new FlameParticle.Factory();
            return this;
        }

        public Builder factory(ParticleFactory factory) {
            this.factory = factory;
            return this;
        }

        public Builder size(float w, float h) {
            properties.set("width", w);
            properties.set("height", h);
            return this;
        }

        public Builder radius(float r) {
            properties.set("radius", r);
            return this;
        }

        public Builder lifetime(float minLifetime, float maxLifetime) {
            this.lifetime = new Range<>(minLifetime, maxLifetime);
            return this;
        }

        public Builder startOffsetX(float min, float max) {
            offsetX = new Range<>(min, max);
            return this;
        }

        public Builder startOffsetY(float min, float max) {
            offsetY = new Range<>(min, max);
            return this;
        }

        public Builder startVelocityX(float min, float max) {
            velocityX = new Range<>(min, max);
            return this;
        }

        public Builder startVelocityY(float min, float max) {
            velocityY = new Range<>(min, max);
            return this;
        }

        public Builder startOffsetX(float value) {
            offsetX = new Range<>(value, value);
            return this;
        }

        public Builder startOffsetY(float value) {
            offsetY = new Range<>(value, value);
            return this;
        }

        public Builder startVelocityX(float value) {
            velocityX = new Range<>(value, value);
            return this;
        }

        public Builder startVelocityY(float value) {
            velocityY = new Range<>(value, value);
            return this;
        }

        public Builder angularVelocity(float min, float max) {
            angularVelocity = new Range<>(min, max);
            return this;
        }

        public Builder angularVelocity(float value) {
            angularVelocity = new Range<>(value, value);
            return this;
        }

        public Builder custom(ParticleAffector affector) {
            affectors.add(affector);
            return this;
        }

        public Builder gravity(float value) {
            affectors.add(new GravityAffector(value));
            return this;
        }

        public Builder drag(float value) {
            affectors.add(new DragAffector(value));
            return this;
        }

        public Builder fade() {
            affectors.add(new FadeAffector());
            return this;
        }

        public Builder _color(float gray) {
            addColorProperty(new Color(gray));
            return this;
        }

        public Builder _color(float gray, float alpha) {
            addColorProperty(new Color(gray, alpha));
            return this;
        }

        public Builder _color(float r, float g, float b) {
            addColorProperty(new Color(r, g, b));
            return this;
        }

        public Builder _color(float r, float g, float b, float a) {
            addColorProperty(new Color(r, g, b, a));
            return this;
        }

        public Builder _color(int rgb) {
            addColorProperty(new Color(rgb));
            return this;
        }

        public Builder _color(String hex) {
            addColorProperty(new Color(hex));
            return this;
        }

        public Builder colors(Color... stops) {
            properties.set("colors", Arrays.asList(stops));
            return this;
        }

        public Builder randomColor() {
            properties.set("random_color", true);
            return this;
        }

        public Builder setProperty(String name, Object value) {
            properties.set(name, value);
            return this;
        }

        private void addColorProperty(Color color) {
            properties.set("color", color);
        }

        public Builder scales(float... stops) {
            List<Float> values = new ArrayList<>(stops.length);

            for (float stop : stops) {
                values.add(stop);
            }

            properties.set("scales", values);
            return this;
        }

        public Builder blend(int blendMode) {
            properties.set("blend_mode", blendMode);
            return this;
        }

        public ParticleTemplate build() {
            ParticleTemplate template = new ParticleTemplate(
                    name,
                    factory,
                    affectors,
                    offsetX,
                    offsetY,
                    velocityX,
                    velocityY,
                    angularVelocity,
                    properties,
                    lifetime
            );

            TEMPLATES.put(name, template);
            return template;
        }

    }

}

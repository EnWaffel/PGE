package de.enwaffel.pge.particle;

import de.enwaffel.pge.Camera;
import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.SceneObject;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter implements SceneObject {

    public PVector pos = new PVector();

    public Camera camera = null;
    public boolean visible = true;

    private final List<ParticleTemplate> templates;
    private final List<Particle> particles = new ArrayList<>();

    public ParticleEmitter(ParticleTemplate... templates) {
        this.templates = new ArrayList<>(List.of(templates));
    }

    public void emit(int amount) {
        for (int i = 0; i < amount; i++) {
            for (ParticleTemplate template : templates) {
                Particle particle = template.makeParticle();
                particle.pos.add(pos);
                particles.add(particle);
            }
        }
    }

    @Override
    public void update(float delta) {
        for (Particle particle : particles) {
            particle.template.stepParticle(particle, delta);
        }

        particles.removeIf(Particle::isDead);
    }

    @Override
    public void draw(DrawContext ctx) {
        for (Particle particle : particles) {
            particle.draw(ctx);
        }
    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setPosition(PVector pos) {
        this.pos = pos;
    }

    @Override
    public PVector getPosition() {
        return pos;
    }

    @Override
    public void onKeyPress(char key) {}

    @Override
    public void onKeyRelease(char key) {}

    @Override
    public void onMousePress(int button) {}

    @Override
    public void onMouseRelease(int button) {}

    @Override
    public void onMouseMove(float x, float y) {}

    @Override
    public void onMouseWheel(float distance) {}

}

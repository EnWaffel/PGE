package de.enwaffel.pge.ui;

import de.enwaffel.pge.*;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.event.MouseEvent;

public class Button implements SceneObject {

    private final Runnable callback;

    public PVector pos = new PVector();
    public PVector size;
    public float rotation = 0f;
    public Camera camera = null;
    public boolean visible = true;
    public Color normalColor = new Color("#505050");
    public Color hoverColor = new Color("#606060");
    public Color pressColor = new Color("#404040");
    public float animationSpeed = 15f;

    private boolean hovering = false;
    private boolean pressed = false;
    private Color curColor = normalColor;

    public Button(String text, float width, float height, Runnable callback) {
        this.size = new PVector(width, height);
        this.callback = callback;
    }

    @Override
    public void update(float delta) {
        if (hovering) {
            curColor = curColor.lerp(hoverColor, getAnimationSpeed(delta));
        } if (pressed) {
            curColor = curColor.lerp(pressColor, getAnimationSpeed(delta));
        } else {
            curColor = curColor.lerp(normalColor, getAnimationSpeed(delta));
        }
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();

        pg.push();
        curColor.apply(pg);
        pg.translate(pos.x, pos.y);
        pg.rotate(PApplet.radians(rotation));

        pg.rect(0, 0, size.x, size.y);

        pg.pop();
    }

    private float getAnimationSpeed(float delta) {
        return animationSpeed < 0 ? 1 : animationSpeed * delta;
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
    public void onMousePress(int button) {
        if (hovering && button == MouseEvent.BUTTON1) {
            pressed = true;
        }
    }

    @Override
    public void onMouseRelease(int button) {
        if (hovering && pressed && button == MouseEvent.BUTTON1) {
            pressed = false;
            callback.run();
        }
    }

    @Override
    public void onMouseMove(float x, float y) {
        hovering = MathUtils.isIntersection(Game.APP.width / 2f + pos.x, Game.APP.height / 2f + pos.y, size.x, size.y, x, y, 1, 1);
        if (!hovering && pressed) pressed = false;
    }

    @Override
    public void onMouseWheel(float distance) {}

}

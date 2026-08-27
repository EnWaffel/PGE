package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Sprite extends Tweenable implements SceneObject {

    public PVector pos = new PVector();
    public PVector size = new PVector();
    public PVector scale = new PVector(1f, 1f);
    public PVector origin = Game.ORIGIN;
    public float rotation = 0;
    public PImage image = null;
    public Camera camera = null;
    public boolean visible = true;
    public Color paintColor = new Color(); // not just "color" because color is a reserved keyword in Processing

    public Sprite() {}

    public Sprite(PImage image) {
        this.image = image;
        size = new PVector(image.width, image.height);
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();

        pg.push();
        pg.translate(pos.x, pos.y);
        pg.scale(scale.x, scale.y);
        pg.rotate(PApplet.radians(rotation));
        pg.translate(-(size.x * origin.x), -(size.y * origin.y));

        if (image != null) {
            pg.scale(size.x / (float)image.width, size.y / (float)image.height);
            pg.image(image, 0, 0);
        } else {
            pg.noStroke();
            paintColor.applyFill(pg);
            pg.rect(0, 0, size.x, size.y);
        }

        pg.pop();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void tweenPosition(float targetX, float targetY, float duration, Easing.EasingFunc func) {
        startTween(new Tween(
                new float[]{ targetX, targetY },
                duration,
                func,
                v -> {
                    pos.x = v[0];
                    pos.y = v[1];
                },
                () -> new float[]{ pos.x, pos.y }
        ));
    }

    public void tweenSize(float targetW, float targetH, float duration, Easing.EasingFunc func) {
        startTween(new Tween(
                new float[]{ targetW, targetH },
                duration,
                func,
                v -> {
                    size.x = v[0];
                    size.y = v[1];
                },
                () -> new float[]{ size.x, size.y }
        ));
    }

    public void tweenScale(float targetX, float targetY, float duration, Easing.EasingFunc func) {
        startTween(new Tween(
                new float[]{ targetX, targetY },
                duration,
                func,
                v -> {
                    scale.x = v[0];
                    scale.y = v[1];
                },
                () -> new float[]{ scale.x, scale.y }
        ));
    }

    public void tweenRotation(float target, float duration, Easing.EasingFunc func) {
        startTween(new Tween(
                new float[]{ target },
                duration,
                func,
                v -> rotation = v[0],
                () -> new float[]{ rotation }
        ));
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

    public void setCenter(PVector worldCenter) {
        pos = new PVector(
                worldCenter.x + size.x * (origin.x - 0.5f),
                worldCenter.y + size.y * (origin.y - 0.5f)
        );
    }

    public PVector getCenter() {
        return new PVector(
                pos.x + size.x * (0.5f - origin.x),
                pos.y + size.y * (0.5f - origin.y)
        );
    }

    public void center(Axis axis) {
        PVector target = getCenter();
        if (axis.equals(Axis.X) || axis.equals(Axis.XY)) target.x = Game.APP.width / 2f;
        if (axis.equals(Axis.Y) || axis.equals(Axis.XY)) target.y = Game.APP.height / 2f;
        setCenter(target);
    }

    public boolean isCollidingWith(Sprite other) {
        return MathUtils.isIntersection(
                pos.x,
                pos.y,
                size.x,
                size.y,
                other.pos.x,
                other.pos.y,
                other.size.x,
                other.size.y
        );
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

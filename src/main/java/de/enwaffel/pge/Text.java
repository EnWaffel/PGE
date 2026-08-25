package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

public class Text extends Tweenable implements SceneObject {

    public PVector pos = new PVector();
    public PVector size = new PVector();
    public PVector scale = new PVector(1f, 1f);
    public PVector origin = new PVector(0.5f, 0.5f);
    public float rotation = 0;
    public Camera camera = null;
    public boolean visible = true;

    private String text = "";
    private float textSize = 12f;

    public Text() {}

    public Text(String text) {
        setText(text);
    }

    public Text(String text, float size) {
        this.textSize = size;
        setText(text);
    }

    private void calcTextSize() {
        PGraphics pg = Game.APP.g;

        PFont oldFont = pg.textFont;
        float oldSize = pg.textSize;
        pg.textSize(textSize);

        size = new PVector(
                pg.textWidth(text),
                pg.textAscent()
        );

        pg.textSize(oldSize);
        if (oldFont != null) pg.textFont(oldFont);
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();

        pg.push();
        pg.translate(pos.x, pos.y);
        pg.scale(scale.x, scale.y);
        pg.rotate(PApplet.radians(rotation));
        pg.translate(-(size.x * origin.x), -(size.y * origin.y));

        pg.textSize(textSize);
        pg.text(text, 0, 0);

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

    public void setText(String text) {
        this.text = text;
        calcTextSize();
    }

    public String getText() {
        return text;
    }

    public void setTextSize(float size) {
        this.textSize = size;
        calcTextSize();
    }

    public float getTextSize() {
        return textSize;
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

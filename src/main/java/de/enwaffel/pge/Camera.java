package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PShader;

import java.util.ArrayList;
import java.util.List;

public class Camera extends Tweenable implements Drawable, Updatable, InputHandler {

    final List<SceneObject> objects = new ArrayList<>();
    private final PGraphics pg;

    public PVector pos = new PVector();
    public float zoom = 1f;
    public float rotation = 0f;
    public PShader shader = null;
    public PVector origin = Game.ORIGIN;

    public float yaw = -90.0f;
    public float pitch = 0;
    public float speed = 3;
    public float sensitivity = 0.1f;

    private SceneObject target = null;
    private float followLag = 0f;
    private final DrawCallback drawCallback;

    private boolean freeMove = false;
    public PVector front = new PVector(0, 0, -1);
    public PVector up = new PVector(0, 1,  0);
    public PVector right = new PVector();
    private float lastX = 0;
    private float lastY = 0;
    private boolean firstMouse = true;

    public Camera() {
        this(null);
    }

    public Camera(DrawCallback drawCallback) {
        this.drawCallback = drawCallback;
        pg = Game.APP.createGraphics(Game.APP.width, Game.APP.height, Game.RENDERER);
    }

    public void add(SceneObject obj) {
        obj.setCamera(this);
        objects.add(obj);
    }

    public void remove(SceneObject obj) {
        obj.setCamera(null);
        objects.remove(obj);
    }

    @Override
    public void draw(DrawContext ctx) {
        pg.beginDraw();
        pg.clear();

        if (shader != null) {
            pg.shader(shader);
        }

        pg.pushMatrix();
        if (freeMove) {
            pg.perspective(PApplet.PI / 3f, (float) pg.width / pg.height, 1f, 20000f);
            pg.camera(
                    pos.x, pos.y, pos.z,
                    pos.x + front.x, pos.y + front.y, pos.z + front.z,
                    0, 1, 0
            );
        } else {
            pg.camera(
                    pg.width / 2f, pg.height / 2f, (pg.height / 2f) / PApplet.tan(PApplet.PI * 30f / 180f),
                    pg.width / 2f, pg.height / 2f, 0,
                    0, 1, 0
            );
            pg.translate(pg.width * origin.x, pg.height * origin.y);
            pg.scale(zoom);
            pg.rotate(rotation);
            pg.translate(-pos.x, -pos.y);
        }

        if (drawCallback != null)
            drawCallback.call(ctx.copyWithNewGraphics(pg));

        for (SceneObject obj : objects) {
            obj.draw(ctx.copyWithNewGraphics(pg));
        }

        pg.popMatrix();
        pg.resetShader();
        pg.endDraw();

        ctx.pg().image(pg, 0, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (target != null) {
            pos = pos.lerp(target.getPosition(), followLag * delta);
        }
    }

    public void follow(SceneObject obj, float lag) {
        target = obj;
        followLag = lag;
    }

    public void stopFollowing() {
        target = null;
        followLag = 0f;
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

    public void tweenZoom(float target, float duration, Easing.EasingFunc func) {
        startTween(new Tween(
                new float[]{target},
                duration,
                func,
                v -> zoom = v[0],
                () -> new float[]{ zoom }
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

    public void enableFreeMove() {
        freeMove = true;
        firstMouse = true;
        pos.set(pg.width / 2f, pg.height / 2f, (pg.height / 2f) / PApplet.tan(PApplet.PI * 30f / 180f));
        yaw = -90f;
        pitch = 0f;
    }

    public void disableFreeMove() {
        freeMove = false;
    }

    public void forward() {
        pos.add(front.copy().mult(speed));
        updateVectors();
    }

    public void backward() {
        pos.sub(front.copy().mult(speed));
        updateVectors();
    }

    public void left() {
        pos.sub(right.copy().mult(speed));
        updateVectors();
    }

    public void right() {
        pos.add(right.copy().mult(speed));
        updateVectors();
    }

    public void up() {
        pos.sub(new PVector(0, 1, 0).mult(speed));
        updateVectors();
    }

    public void down() {
        pos.add(new PVector(0, 1, 0).mult(speed));
        updateVectors();
    }

    public void updateVectors() {
        PVector direction = new PVector(
                PApplet.cos(PApplet.radians(yaw)) * PApplet.cos(PApplet.radians(pitch)),
                PApplet.sin(PApplet.radians(pitch)),
                PApplet.sin(PApplet.radians(yaw)) * PApplet.cos(PApplet.radians(pitch))
        );
        front = direction.normalize();
        right = front.copy().cross(new PVector(0, 1, 0)).normalize();
        up = right.copy().cross(front).normalize();
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
    public void onMouseMove(float x, float y) {
        if (!freeMove) return;

        if (firstMouse) {
            lastX = x;
            lastY = y;
            firstMouse = false;
        }

        float offsetX = x - lastX;
        float offsetY = lastY - y;
        lastX = x;
        lastY = y;

        offsetX *= sensitivity;
        offsetY *= sensitivity;

        yaw += offsetX;
        pitch -= offsetY;

        if (pitch > 89.0f)
            pitch =  89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;

        updateVectors();
    }

    @Override
    public void onMouseWheel(float distance) {}

    public interface DrawCallback {
        void call(DrawContext ctx);
    }

}

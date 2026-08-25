package de.enwaffel.pge;

import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PShader;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PConstants.P2D;

public class Camera extends Tweenable implements Drawable, Updatable {

    final List<SceneObject> objects = new ArrayList<>();
    private final PGraphics pg;

    public PVector pos = new PVector();
    public float zoom = 1f;
    public float rotation = 0f;
    public PShader shader = null;

    private SceneObject target = null;
    private float followLag = 0f;

    public Camera() {
        pg = Game.APP.createGraphics(Game.APP.width, Game.APP.height, P2D);
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
        pg.translate(pg.width / 2f, pg.height / 2f);
        pg.scale(zoom);
        pg.rotate(rotation);
        pg.translate(-pos.x, -pos.y);

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

}

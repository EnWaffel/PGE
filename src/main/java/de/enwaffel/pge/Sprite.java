package de.enwaffel.pge;

import de.enwaffel.pge.atlas.AtlasFrame;
import de.enwaffel.pge.atlas.TextureAtlas;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sprite extends Tweenable implements SceneObject {

    public PVector pos = new PVector();
    public PVector size = new PVector();
    public PVector scale = new PVector(1f, 1f);
    public PVector origin = Game.ORIGIN.copy();
    public float rotation = 0;
    public PImage image = null;
    public TextureAtlas textureAtlas = null;
    public Camera camera = null;
    public boolean visible = true;
    public Color paintColor = new Color(); // not just "color" because it's a reserved keyword in Processing

    private final Map<String, Animation> animations = new HashMap<>();
    private Animation curAnim = null;
    private boolean animPlaying = false;

    public Sprite() {}

    public Sprite(PImage image) {
        this.image = image;
        size = new PVector(image.width, image.height);
    }

    public Sprite(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public void addAnimationByPrefix(String name, String prefix, int fps, boolean loop) {
        if (textureAtlas == null) {
            System.err.println("Can't add animation: missing texture atlas");
            return;
        }
        if (animations.containsKey(name)) {
            System.err.println("Can't add animation: already exists: " + name);
            return;
        }

        List<AtlasFrame> frames = textureAtlas.getFramesByPrefix(prefix);
        if (frames.isEmpty()) {
            System.err.println("Can't add animation: not found in atlas: " + prefix);
            return;
        }

        animations.put(
                name,
                new Animation(
                        frames,
                        fps,
                        loop
                )
        );
    }

    public void playAnimation(String name) {
        if (!animations.containsKey(name)) {
            System.err.println("Can't play animation: not found: " + name);
            return;
        }

        curAnim = animations.get(name);
        curAnim.play();
        animPlaying = true;
    }

    public void stopAnimation() {
        animPlaying = false;
        curAnim = null;
    }

    @Override
    public void draw(DrawContext ctx) {
        PGraphics pg = ctx.pg();

        AtlasFrame curFrame = curAnim != null ? curAnim.getCurrentFrame() : null;

        float w = size.x, h = size.y;
        float frameRot = 0f;
        if (curFrame != null) {
            w = curFrame.sourceSize().x;
            h = curFrame.sourceSize().y;
            size.set(w, h);

            frameRot = curFrame.rotation();
        }

        pg.push();
        pg.translate(pos.x, pos.y);
        pg.scale(scale.x, scale.y);
        pg.rotate(PApplet.radians(rotation + frameRot));
        pg.translate(-(w * origin.x), -(h * origin.y));

        if (curFrame != null) {
            int sx = (int) curFrame.framePos().x;
            int sy = (int) curFrame.framePos().y;
            int sw = (int) curFrame.frameSize().x;
            int sh = (int) curFrame.frameSize().y;

            pg.image(textureAtlas.getImage(),
                    curFrame.trimOffset().x, curFrame.trimOffset().y, sw, sh,
                    sx, sy, sx + sw, sy + sh);
        } else if (image != null) {
            pg.scale(w / (float) image.width, h / (float) image.height);
            pg.image(image, 0, 0);
        } else {
            pg.noStroke();
            paintColor.applyFill(pg);
            pg.rect(0, 0, w, h);
        }

        pg.pop();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (curAnim != null) {
            curAnim.update(delta);
        }
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

    public boolean isAnimationPlaying() {
        return animPlaying;
    }

    public Animation getPlayingAnimation() {
        return curAnim;
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

    public Rect getBounds() {
        float rad = PApplet.radians(rotation);
        float cos = PApplet.cos(rad);
        float sin = PApplet.sin(rad);
        float ox = size.x * origin.x;
        float oy = size.y * origin.y;

        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE, maxY = -Float.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            float lx = ((i & 1) == 0 ? 0 : size.x) - ox;
            float ly = (i < 2 ? 0 : size.y) - oy;

            float wx = pos.x + (lx * cos - ly * sin) * scale.x;
            float wy = pos.y + (lx * sin + ly * cos) * scale.y;

            minX = Math.min(minX, wx);  maxX = Math.max(maxX, wx);
            minY = Math.min(minY, wy);  maxY = Math.max(maxY, wy);
        }

        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    public boolean isCollidingWith(Sprite other) {
        return getBounds().intersects(other.getBounds());
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

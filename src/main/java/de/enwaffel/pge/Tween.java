package de.enwaffel.pge;

import processing.core.PApplet;

import java.util.function.Supplier;
import java.util.function.Consumer;

public class Tween {

    private final float[] target;
    private final float duration;
    private final Easing.EasingFunc func;
    private final Consumer<float[]> setter;
    private final Supplier<float[]> getter;

    private float elapsed = 0f;
    private float[] start;
    private float[] current;
    private boolean started = false;

    public Tween(float[] target, float duration, Easing.EasingFunc func, Consumer<float[]> setter, Supplier<float[]> getter) {
        this.target = target;
        this.duration = duration;
        this.func = func;
        this.setter = setter;
        this.getter = getter;
    }

    public void start() {
        start = getter.get().clone();
        current = new float[target.length];
        elapsed = 0f;
        started = true;
    }

    public void update(float delta) {
        if (!started) {
            start();
        }

        elapsed += delta;
        float t = PApplet.constrain(elapsed / duration, 0f, 1f);
        float eased = func.calc(t);

        for (int i = 0; i < target.length; i++) {
            current[i] = PApplet.lerp(start[i], target[i], eased);
        }

        setter.accept(current);
    }

    public boolean isFinished() {
        return elapsed >= duration;
    }

}

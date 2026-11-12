package de.enwaffel.pge;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tweenable {

    private final List<Tween> tweens = new CopyOnWriteArrayList<>();

    public void startTween(Tween tween) {
        tween.start();
        tweens.add(tween);
    }

    public void cancelTween(Tween tween) {
        tweens.remove(tween);
    }

    public void update(float delta) {
        for (Tween tween : tweens) {
            tween.update(delta);
        }

        tweens.removeIf(Tween::isFinished);
    }

}

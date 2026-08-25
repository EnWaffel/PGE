package de.enwaffel.pge;

import java.util.List;
import java.util.ArrayList;

public abstract class Tweenable {

    private final List<Tween> tweens = new ArrayList<>();

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

package de.enwaffel.pge;

import de.enwaffel.pge.atlas.AtlasFrame;

import java.util.List;

public class Animation {

    private final List<AtlasFrame> frames;
    private final float frameTime;
    private final boolean loop;

    private int index = 0;
    private float waitedTime = 0;
    private boolean finished = false;

    Animation(List<AtlasFrame> frames, int fps, boolean loop) {
        this.frames = frames;
        this.frameTime = 1.0f / Math.max(fps, 1);
        this.loop = loop;
    }

    public void play() {
        index = 0;
        waitedTime = 0;
        finished = false;
    }

    public void update(float delta) {
        if (finished || frames.isEmpty()) return;

        waitedTime += delta;

        while (waitedTime >= frameTime) {
            waitedTime -= frameTime;
            index++;

            if (index >= frames.size()) {
                if (loop) {
                    index = 0;
                } else {
                    index = frames.size() - 1;
                    finished = true;
                    waitedTime = 0;
                    return;
                }
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public AtlasFrame getCurrentFrame() {
        return frames.get(index);
    }

}
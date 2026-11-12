package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

public class SoundSource implements Updatable {

    private final SoundFile file;
    private boolean paused = false;

    public PVector pos = new PVector();
    public float volume = 1f;

    public SoundSource(String name) {
        file = new SoundFile(Game.APP, Assets.getSound(name));
    }

    public void play() {
        paused = false;
        file.play();
    }

    public void pause() {
        paused = true;
        file.pause();
    }

    public void stop() {
        file.stop();
    }

    public void setRate(float v) {
        file.rate(v);
    }

    public void jumpTo(float v) {
        file.jump(v);
    }

    public float getTimePosition() {
        return file.position();
    }

    public boolean isPlaying() {
        return file.isPlaying();
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void update(float delta) {
        if (file.channels() > 1) return;

        PVector pDelta = PVector.sub(pos, SoundSystem.LISTENER_POSITION);
        float dist = pDelta.mag();

        float pan = PApplet.constrain(pDelta.x / SoundSystem.PAN_RANGE, -1, 1);
        float vol = PApplet.constrain(PApplet.map(dist, 0, SoundSystem.MAX_DISTANCE, 1, 0), 0, 1);

        file.pan(pan);
        file.amp(volume * vol);
    }

}

package de.enwaffel.pge;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

public class SoundSource implements Updatable {

    private final SoundFile file;

    public PVector pos = new PVector();
    public float volume = 1f;

    public SoundSource(String name) {
        file = new SoundFile(Game.APP, Assets.getSound(name));
    }

    public void play() {
        file.play();
    }

    public void pause() {
        file.pause();
    }

    public void stop() {
        file.stop();
    }

    public float getTimePosition() {
        return file.position();
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

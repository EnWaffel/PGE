package de.enwaffel.pge;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class SoundSystem {

    public static float MAX_DISTANCE = 500;
    public static float PAN_RANGE = 300;
    public static PVector LISTENER_POSITION = new PVector();

    private static final HashMap<String, List<SoundSource>> PLAYING = new HashMap<>();

    private SoundSystem() {}

    public static SoundSource play(String name) {
        SoundSource soundSource = new SoundSource(name);

        if (PLAYING.containsKey(name)) {
            PLAYING.get(name).add(soundSource);
        } else {
            PLAYING.put(name, new ArrayList<>(List.of(soundSource)));
        }

        soundSource.play();
        return soundSource;
    }

    public static void stopAll(String name) {
        if (!PLAYING.containsKey(name)) return;
        List<SoundSource> soundSources = PLAYING.get(name);
        soundSources.forEach(SoundSource::stop);
        soundSources.clear();
    }

    public static void update(float delta) {
        for (List<SoundSource> sources : PLAYING.values()) {
            for (SoundSource source : sources) {
                source.update(delta);
            }
        }
    }

}

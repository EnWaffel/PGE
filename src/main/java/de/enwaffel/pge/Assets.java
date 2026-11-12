package de.enwaffel.pge;

import processing.core.PImage;
import processing.sound.SoundFile;

import java.nio.file.Path;
import java.util.HashMap;

public final class Assets {

    public static Path BASE_PATH = Path.of("");
    public static HashMap<String, PImage> IMAGES = new HashMap<>();
    public static HashMap<String, SoundFile> SOUNDS = new HashMap<>();

    private Assets() {}

    public static PImage loadImage(String name, String path) {
        if (IMAGES.containsKey(name)) return IMAGES.get(name);
        PImage image = Game.APP.loadImage(BASE_PATH.resolve(path).toString());

        IMAGES.put(name, image);
        return image;
    }

    public static PImage getImage(String name) {
        if (!IMAGES.containsKey(name)) throw new NullPointerException("Bild nicht geladen: " + name);
        return IMAGES.get(name);
    }

    public static SoundFile loadSound(String name, String path) {
        if (SOUNDS.containsKey(name)) return SOUNDS.get(name);

        SOUNDS.put(name, new SoundFile(Game.APP, BASE_PATH.resolve(path).toString()));
        return getSound(name);
    }

    public static SoundFile getSound(String name) {
        if (!SOUNDS.containsKey(name)) throw new NullPointerException("Audio nicht geladen: " + name);
        return SOUNDS.get(name);
    }

}

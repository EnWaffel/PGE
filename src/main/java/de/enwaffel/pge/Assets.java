package de.enwaffel.pge;

import processing.core.PImage;
import processing.sound.SoundFile;

import java.nio.file.Path;
import java.util.HashMap;

public final class Assets {

    public static Path BASE_PATH = Path.of("");
    public static HashMap<String, PImage> IMAGES = new HashMap<>();
    public static HashMap<String, String> SOUNDS = new HashMap<>();

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

    public static String loadSound(String name, String path) {
        if (SOUNDS.containsKey(name)) return SOUNDS.get(name);
        new SoundFile(Game.APP, path);

        SOUNDS.put(name, path);
        return path;
    }

    public static String getSound(String name) {
        if (!SOUNDS.containsKey(name)) throw new NullPointerException("Audio nicht geladen: " + name);
        return SOUNDS.get(name);
    }

}

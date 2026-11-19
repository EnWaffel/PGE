package de.enwaffel.pge.atlas;

import de.enwaffel.pge.Game;
import de.enwaffel.pge.utils.Pair;
import processing.core.PImage;

import java.util.List;

public class TextureAtlas {

    private final List<Pair<String, AtlasFrame>> frames;
    private final PImage image;

    TextureAtlas(List<Pair<String, AtlasFrame>> frames, PImage image) {
        this.frames = frames;
        this.image = image;
    }

    public List<AtlasFrame> getFramesByPrefix(String prefix) {
        return frames
                .stream()
                .filter(f -> f.value1().startsWith(prefix))
                .map(Pair::value2)
                .toList();
    }

    public List<Pair<String, AtlasFrame>> getFrames() {
        return frames;
    }

    public PImage getImage() {
        return image;
    }

    public static TextureAtlas load(String dataPath, String imagePath, Format format) {
        List<Pair<String, AtlasFrame>> frames;

        switch (format) {
            case JSON_ARRAY -> frames = JsonArrayLoader.loadFrames(dataPath);
            case JSON_HASH -> frames = JsonHashLoader.loadFrames(dataPath);
            default -> throw new IllegalArgumentException("Invalid texture atlas format: " + format.name());
        }

        return new TextureAtlas(
                frames,
                Game.APP.loadImage(imagePath)
        );
    }

    public enum Format {
        JSON_ARRAY,
        JSON_HASH
    }

}

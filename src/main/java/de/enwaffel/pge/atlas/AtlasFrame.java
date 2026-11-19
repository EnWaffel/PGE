package de.enwaffel.pge.atlas;

import org.json.JSONObject;
import processing.core.PVector;

public record AtlasFrame(
        PVector framePos,
        PVector frameSize,
        PVector trimOffset,
        PVector sourceSize,
        float rotation
) {

    public static AtlasFrame fromJSON(JSONObject obj) {
        JSONObject frame = obj.getJSONObject("frame");
        JSONObject sprite = obj.getJSONObject("spriteSourceSize");
        JSONObject source = obj.getJSONObject("sourceSize");

        boolean isRotated = obj.getBoolean("rotated");

        return new AtlasFrame(
                new PVector(frame.getFloat("x"), frame.getFloat("y")),
                new PVector(frame.getFloat(isRotated ? "h" : "w"), frame.getFloat(isRotated ? "w" : "h")),
                new PVector(sprite.getFloat(isRotated ? "y" : "x"), sprite.getFloat(isRotated ? "x" : "y")),
                new PVector(source.getFloat("w"), source.getFloat("h")),
                isRotated ? -90.0f : 0f
        );
    }

}

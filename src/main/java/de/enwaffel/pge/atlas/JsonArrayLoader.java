package de.enwaffel.pge.atlas;

import de.enwaffel.pge.TextFile;
import de.enwaffel.pge.utils.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JsonArrayLoader {

    static List<Pair<String, AtlasFrame>> loadFrames(String dataPath) {
        JSONObject obj;
        try {
            obj = new JSONObject(Objects.requireNonNull(new TextFile(dataPath).readAll()));
        } catch (Exception e) {
            System.err.println("Failed to read file: " + dataPath);
            return null;
        }

        List<Pair<String, AtlasFrame>> frames = new ArrayList<>();

        JSONArray framesArr = obj.getJSONArray("frames");
        for (Object o : framesArr) {
            JSONObject frameObj = (JSONObject) o;

            frames.add(new Pair<>(
                    frameObj.getString("filename"),
                    AtlasFrame.fromJSON(frameObj)
            ));
        }

        return frames;
    }

}

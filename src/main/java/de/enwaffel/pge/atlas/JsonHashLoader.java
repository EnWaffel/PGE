package de.enwaffel.pge.atlas;

import de.enwaffel.pge.TextFile;
import de.enwaffel.pge.utils.Pair;
import org.json.JSONObject;

import java.util.*;

public class JsonHashLoader {

    static List<Pair<String, AtlasFrame>> loadFrames(String dataPath) {
        JSONObject obj;
        try {
            obj = new JSONObject(Objects.requireNonNull(new TextFile(dataPath).readAll()));
        } catch (Exception e) {
            System.err.println("Failed to read file: " + dataPath);
            return null;
        }

        List<Pair<String, AtlasFrame>> frames = new ArrayList<>();

        JSONObject framesObj = obj.getJSONObject("frames");
        for (Object o : framesObj.names()) {
            String name = (String) o;
            JSONObject frameObj = framesObj.getJSONObject(name);

            frames.add(new Pair<>(
                    name,
                    AtlasFrame.fromJSON(frameObj)
            ));
        }

        return frames;
    }

}

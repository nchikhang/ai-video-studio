package com.aivideostudio.character;

import com.aivideostudio.loader.YamlLoader;
import com.aivideostudio.model.CharacterProfile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterLoader {

    @SuppressWarnings("unchecked")
    public CharacterRegistry load(Path file) throws IOException {

        YamlLoader yamlLoader = new YamlLoader();

        Map<String,Object> root = yamlLoader.load(file);

        CharacterRegistry registry = new CharacterRegistry();

        Map<String, Object> characters =
                (Map<String, Object>) root.get("characters");

        if (characters == null) {
            return registry;
        }

        for (Map.Entry<String, Object> entry : characters.entrySet()) {

            String id = entry.getKey();

            LinkedHashMap<String, Object> values =
                    (LinkedHashMap<String, Object>) entry.getValue();

            CharacterProfile profile = new CharacterProfile();

            profile.setId(id);
            profile.setDisplayName((String) values.get("displayName"));
            profile.setVoice((String) values.get("voice"));
            profile.setRate((String) values.get("rate"));
            profile.setPitch((String) values.get("pitch"));
            profile.setImageFolder((String) values.get("imageFolder"));
            profile.setDefaultPose((String) values.get("defaultPose"));
            profile.setSubtitleColor((String) values.get("subtitleColor"));
            profile.setDefaultAnimation((String) values.get("defaultAnimation"));

            registry.register(profile);

        }

        return registry;

    }

}
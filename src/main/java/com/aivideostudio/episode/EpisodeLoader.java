package com.aivideostudio.episode;

import com.aivideostudio.loader.YamlLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class EpisodeLoader {

    @SuppressWarnings("unchecked")
    public Episode load(Path file) throws Exception {

        YamlLoader yamlLoader = new YamlLoader();

        Map<String,Object> root = yamlLoader.load(file);

        Episode episode = new Episode();

        Map<String, Object> info =
                (Map<String, Object>) root.get("episode");

        episode.setId((String) info.get("id"));
        episode.setTitle((String) info.get("title"));
        episode.setMusic((String) info.get("music"));

        List<Map<String, Object>> scenes =
                (List<Map<String, Object>>) root.get("scenes");

        int index = 1;

        for (Map<String, Object> item : scenes) {

            Scene scene = new Scene();

            scene.setIndex(index++);

            scene.setBackground((String) item.get("background"));
            scene.setCharacter((String) item.get("character"));
            scene.setPose((String) item.get("pose"));
            scene.setText((String) item.get("text"));
            scene.setProp((String) item.get("prop"));
            scene.setCard((String) item.get("card"));

            episode.addScene(scene);

        }

        return episode;

    }

}
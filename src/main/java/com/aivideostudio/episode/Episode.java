package com.aivideostudio.episode;

import java.util.ArrayList;
import java.util.List;

public class Episode {

    private String id;

    private String title;

    private String music;

    private final List<Scene> scenes = new ArrayList<>();

    public Episode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }
}
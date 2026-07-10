package com.aivideostudio.render;

import java.util.ArrayList;
import java.util.List;

public class RenderManifest {

    private int width = 1920;

    private int height = 1080;

    private int fps = 30;

    private final List<RenderClip> clips = new ArrayList<>();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFps() {
        return fps;
    }

    public List<RenderClip> getClips() {
        return clips;
    }

    public void add(RenderClip clip) {
        clips.add(clip);
    }

}
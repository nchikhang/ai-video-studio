package com.aivideostudio.composition;

import java.util.ArrayList;
import java.util.List;

public class Composition {

    private final List<CompositionClip> clips = new ArrayList<>();

    public void add(CompositionClip clip) {
        clips.add(clip);
    }

    public List<CompositionClip> getClips() {
        return clips;
    }

}
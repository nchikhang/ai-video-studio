package com.aivideostudio.timeline;

import java.util.ArrayList;
import java.util.List;

public class Timeline {

    private final List<TimelineClip> clips = new ArrayList<>();

    public List<TimelineClip> getClips() {
        return clips;
    }

    public void add(TimelineClip clip) {
        clips.add(clip);
    }

}
package com.aivideostudio.composition;

import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

public class CompositionBuilder {

    public Composition build(Timeline timeline) {

        Composition composition = new Composition();

        for (TimelineClip clip : timeline.getClips()) {

            CompositionClip background = new CompositionClip();

            background.setTrack(TrackType.BACKGROUND);

            background.setLayer(0);

            background.setStart(clip.getStartTime());

            background.setDuration(clip.getDuration());

            background.setEnd(clip.getEndTime());

            composition.add(background);

            CompositionClip audio = new CompositionClip();

            audio.setTrack(TrackType.AUDIO);

            audio.setLayer(1);

            audio.setFile(clip.getAudioFile());

            audio.setStart(clip.getStartTime());

            audio.setDuration(clip.getDuration());

            audio.setEnd(clip.getEndTime());

            composition.add(audio);

        }

        return composition;

    }

}
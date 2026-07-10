package com.aivideostudio.render;

import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

public class RenderManifestBuilder {

    public RenderManifest build(Timeline timeline) {

        RenderManifest manifest = new RenderManifest();

        for (TimelineClip clip : timeline.getClips()) {

            RenderClip rc = new RenderClip();

            rc.setIndex(clip.getIndex());

            rc.setCharacter(clip.getCharacter());

            rc.setPose(clip.getPose());

            rc.setBackground(clip.getBackgroundImage());

            rc.setCharacterImage(clip.getCharacterImage());

            rc.setAudio(clip.getAudioFile());

            rc.setText(clip.getText());

            rc.setStart(clip.getStartTime());

            rc.setDuration(clip.getDuration());

            rc.setEnd(clip.getEndTime());

            manifest.add(rc);

        }

        return manifest;

    }

}
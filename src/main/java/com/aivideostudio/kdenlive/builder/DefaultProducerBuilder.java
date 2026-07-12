package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.repository.ProducerRepository;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

/**
 * Maps the Timeline into MLT producers (bin clips), de-duplicated by resource.
 * One image producer per distinct background / character / prop / card, and one
 * audio producer per voice clip.
 */
public class DefaultProducerBuilder implements ProducerBuilder {

    @Override
    public void build(BuildContext context) {
        Timeline timeline = context.getPipelineContext().getTimeline();
        if (timeline == null) return;

        ProducerRepository repo =
                context.getRepositories().getProducerRepository();

        for (TimelineClip clip : timeline.getClips()) {
            if (clip.getBackgroundImage() != null)
                repo.createImage(clip.getBackgroundImage().toString());
            if (clip.getCharacterImage() != null)
                repo.createImage(clip.getCharacterImage().toString());
            if (clip.getPropImage() != null)
                repo.createImage(clip.getPropImage().toString());
            if (clip.getCardImage() != null)
                repo.createImage(clip.getCardImage().toString());
            if (clip.getAudioFile() != null) {
                long ms = Math.round(clip.getDuration() * 1000.0);
                repo.createAudio(clip.getAudioFile().toString(), ms);
            }
        }
    }
}
package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.repository.ProducerRepository;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

/**
 * Maps the Timeline into MLT producers (bin clips), de-duplicated by resource.
 * Adds an optional continuous background-music producer (episode.music).
 */
public class DefaultProducerBuilder implements ProducerBuilder {

    /** Long enough to cover any episode; the playlist entry clips it to timeline length. */
    private static final long MUSIC_DURATION_MS = 3_600_000;

    @Override
    public void build(BuildContext context) {
        Timeline timeline = context.getPipelineContext().getTimeline();
        if (timeline == null) return;

        ProducerRepository repo = context.getRepositories().getProducerRepository();

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

        Episode episode = context.getPipelineContext().getEpisode();
        if (episode != null && episode.getMusic() != null) {
            repo.createAudio(musicResource(episode.getMusic()), MUSIC_DURATION_MS);
        }
    }

    /** Shared so ProducerBuilder and PlaylistBuilder resolve the same music path. */
    public static String musicResource(String music) {
        String name = music.contains(".") ? music : music + ".mp3";
        return ProjectPaths.MUSIC.resolve(name).toString();
    }
}

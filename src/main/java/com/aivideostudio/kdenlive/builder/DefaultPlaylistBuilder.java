package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.repository.PlaylistRepository;
import com.aivideostudio.kdenlive.builder.repository.ProducerRepository;
import com.aivideostudio.kdenlive.model.Playlist;
import com.aivideostudio.kdenlive.model.PlaylistEntry;
import com.aivideostudio.kdenlive.model.Producer;
import com.aivideostudio.kdenlive.support.FrameUtil;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

import java.nio.file.Path;

/**
 * One playlist per timeline layer: audio / background / character / hero-prop / word-card.
 * Playlists are registered in the BuildContext playlist index under logical keys so the
 * TractorBuilder can wire each track to its playlist. Gaps (e.g. a scene with no prop)
 * become blanks automatically in the XML writer via PlaylistEntry.timelineFrame.
 */
public class DefaultPlaylistBuilder implements PlaylistBuilder {

    public static final String AUDIO = "audio";
    public static final String BACKGROUND = "background";
    public static final String CHARACTER = "character";
    public static final String HERO = "hero";
    public static final String CARD = "card";

    @Override
    public void build(BuildContext context) {
        Timeline timeline = context.getPipelineContext().getTimeline();
        if (timeline == null) return;

        double fps = context.getProject().getProfile().getFps();
        PlaylistRepository playlists = context.getRepositories().getPlaylistRepository();
        ProducerRepository producers = context.getRepositories().getProducerRepository();

        Playlist audio = register(context, AUDIO, playlists.create());
        Playlist background = register(context, BACKGROUND, playlists.create());
        Playlist character = register(context, CHARACTER, playlists.create());
        Playlist hero = register(context, HERO, playlists.create());
        Playlist card = register(context, CARD, playlists.create());

        for (TimelineClip clip : timeline.getClips()) {
            long start = FrameUtil.secondsToFrame(clip.getStartTime(), fps);
            long dur = FrameUtil.secondsToFrame(clip.getDuration(), fps);

            addEntry(background, producers, clip.getBackgroundImage(), start, dur);
            addEntry(character, producers, clip.getCharacterImage(), start, dur);
            addEntry(hero, producers, clip.getPropImage(), start, dur);
            addEntry(card, producers, clip.getCardImage(), start, dur);
            addEntry(audio, producers, clip.getAudioFile(), start, dur);
        }
    }

    private Playlist register(BuildContext ctx, String key, Playlist pl) {
        ctx.getPlaylistIndex().put(key, pl);
        return pl;
    }

    private void addEntry(Playlist playlist, ProducerRepository producers,
                          Path resource, long start, long dur) {
        if (resource == null) return;
        Producer producer = producers.findByResource(resource.toString());
        if (producer == null) return;
        PlaylistEntry entry =
                new PlaylistEntry(producer.getId(), 0, Math.max(dur - 1, 0));
        entry.setTimelineFrame(start);
        playlist.getEntries().add(entry);
    }
}
package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.composition.Composer;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.repository.PlaylistRepository;
import com.aivideostudio.kdenlive.builder.repository.ProducerRepository;
import com.aivideostudio.kdenlive.model.Filter;
import com.aivideostudio.kdenlive.model.Playlist;
import com.aivideostudio.kdenlive.model.PlaylistEntry;
import com.aivideostudio.kdenlive.model.Producer;
import com.aivideostudio.kdenlive.support.FrameUtil;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * One playlist per layer: audio / background / character / hero / card (+ optional music).
 * Each video layer gets a qtblend transform from the {@link Composer} (auto-composition):
 * background full-frame, character docked, hero pops in, card at top.
 */
public class DefaultPlaylistBuilder implements PlaylistBuilder {

    public static final String AUDIO = "audio";
    public static final String MUSIC = "music";
    public static final String BACKGROUND = "background";
    public static final String CHARACTER = "character";
    public static final String HERO = "hero";
    public static final String CARD = "card";

    @Override
    public void build(BuildContext context) {
        Timeline timeline = context.getPipelineContext().getTimeline();
        if (timeline == null) return;

        double fps = context.getProject().getProfile().getFps();
        Composer composer = new Composer(context.getProject().getProfile());
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

            addEntry(background, producers, clip.getBackgroundImage(), start, dur, composer::background);
            addEntry(character, producers, clip.getCharacterImage(), start, dur, composer::character);
            addEntry(hero, producers, clip.getPropImage(), start, dur, composer::hero);
            addEntry(card, producers, clip.getCardImage(), start, dur, composer::card);
            addEntry(audio, producers, clip.getAudioFile(), start, dur, null);
        }

        addMusicBed(context, composer, playlists, producers, fps);
    }

    private Playlist register(BuildContext ctx, String key, Playlist pl) {
        ctx.getPlaylistIndex().put(key, pl);
        return pl;
    }

    private void addEntry(Playlist playlist, ProducerRepository producers,
                          Path resource, long start, long dur,
                          Supplier<Filter> transform) {
        if (resource == null) return;
        Producer producer = producers.findByResource(resource.toString());
        if (producer == null) return;
        PlaylistEntry entry = new PlaylistEntry(producer.getId(), 0, Math.max(dur - 1, 0));
        entry.setTimelineFrame(start);
        if (transform != null) entry.getFilters().add(transform.get());
        playlist.getEntries().add(entry);
    }

    /** Continuous background music spanning the whole timeline, reduced under the VO. */
    private void addMusicBed(BuildContext ctx, Composer composer,
                             PlaylistRepository playlists, ProducerRepository producers,
                             double fps) {
        Episode episode = ctx.getPipelineContext().getEpisode();
        if (episode == null || episode.getMusic() == null) return;

        Producer music = producers.findByResource(
                DefaultProducerBuilder.musicResource(episode.getMusic()));
        if (music == null) return;

        long total = totalFrames(ctx);
        if (total <= 0) return;

        Playlist musicPlaylist = register(ctx, MUSIC, playlists.create());
        PlaylistEntry entry = new PlaylistEntry(music.getId(), 0, total - 1);
        entry.setTimelineFrame(0);
        entry.getFilters().add(composer.musicVolume());
        musicPlaylist.getEntries().add(entry);
    }

    private long totalFrames(BuildContext ctx) {
        long max = 0;
        for (Playlist pl : ctx.getProject().getPlaylists()) {
            for (PlaylistEntry en : pl.getEntries()) {
                max = Math.max(max, en.getTimelineFrame() + en.getDuration());
            }
        }
        return max;
    }
}

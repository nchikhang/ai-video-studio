package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.repository.TrackRepository;
import com.aivideostudio.kdenlive.model.*;
import com.aivideostudio.kdenlive.support.FrameUtil;
import com.aivideostudio.timeline.TimelineClip;

/**
 * Declares the logical timeline tracks (audio first, then video bottom->top:
 * background, character, hero-prop, card) and copies scene starts as chapter markers.
 * The black background track, per-track sub-tractors and compositing transitions are
 * synthesised by {@link com.aivideostudio.kdenlive.writer.KdenliveXmlWriter}.
 */
public class DefaultTractorBuilder implements TractorBuilder {

    @Override
    public void build(BuildContext context) {
        KdenliveProject project = context.getProject();
        project.setTractor(new Tractor());

        TrackRepository tracks = context.getRepositories().getTrackRepository();
        track(context, tracks, DefaultPlaylistBuilder.AUDIO, TrackType.AUDIO);
        track(context, tracks, DefaultPlaylistBuilder.BACKGROUND, TrackType.VIDEO);
        track(context, tracks, DefaultPlaylistBuilder.CHARACTER, TrackType.VIDEO);
        track(context, tracks, DefaultPlaylistBuilder.HERO, TrackType.VIDEO);
        track(context, tracks, DefaultPlaylistBuilder.CARD, TrackType.VIDEO);

        addMarkers(context);
    }

    private void track(BuildContext ctx, TrackRepository repo,
                       String key, TrackType type) {
        Playlist pl = ctx.getPlaylistIndex().get(key);
        if (pl == null) return;
        Track t = new Track(ctx.getIds().tracks().next(), pl.getId(), type);
        repo.save(t);
    }

    private void addMarkers(BuildContext ctx) {
        if (ctx.getPipelineContext().getTimeline() == null) return;
        double fps = ctx.getProject().getProfile().getFps();
        int i = 0;
        for (TimelineClip clip : ctx.getPipelineContext().getTimeline().getClips()) {
            String name = clip.getText() != null ? clip.getText() : "Scene " + (i + 1);
            long frame = FrameUtil.secondsToFrame(clip.getStartTime(), fps);
            ctx.getProject().getMarkers().add(
                    new Marker(String.valueOf(i++), name, frame));
        }
    }
}
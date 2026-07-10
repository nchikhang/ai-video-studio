package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Track;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrackRepository {

    private final BuildContext context;

    private final Map<String, Track> tracks =
            new LinkedHashMap<>();

    public TrackRepository(BuildContext context) {
        this.context = context;
    }

    public Track save(Track track) {

        tracks.put(
                track.getId(),
                track);

        if (context.getProject().getTractor() != null) {

            context.getProject()
                    .getTractor()
                    .getTracks()
                    .add(track);

        }

        return track;

    }

    public Track find(String id) {
        return tracks.get(id);
    }

    public Collection<Track> findAll() {
        return tracks.values();
    }

}
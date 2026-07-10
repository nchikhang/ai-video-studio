package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Playlist;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlaylistRepository {

    private final BuildContext context;

    private final Map<String, Playlist> playlists =
            new LinkedHashMap<>();

    public PlaylistRepository(BuildContext context) {
        this.context = context;
    }

    public Playlist create() {

        Playlist playlist = new Playlist();

        playlist.setId(
                context.getIds()
                        .playlists()
                        .next());

        playlists.put(
                playlist.getId(),
                playlist);

        context.getProject()
                .getPlaylists()
                .add(playlist);

        return playlist;

    }

    public Playlist find(String id) {
        return playlists.get(id);
    }

    public Collection<Playlist> findAll() {
        return playlists.values();
    }

}
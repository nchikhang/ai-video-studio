package com.aivideostudio.kdenlive.support;

public class KdenliveIds {

    private final IdGenerator producerIds =
            new IdGenerator("producer");

    private final IdGenerator playlistIds =
            new IdGenerator("playlist");

    private final IdGenerator trackIds =
            new IdGenerator("track");

    private final IdGenerator transitionIds =
            new IdGenerator("transition");

    private final IdGenerator filterIds =
            new IdGenerator("filter");

    public IdGenerator producers() {
        return producerIds;
    }

    public IdGenerator playlists() {
        return playlistIds;
    }

    public IdGenerator tracks() {
        return trackIds;
    }

    public IdGenerator transitions() {
        return transitionIds;
    }

    public IdGenerator filters() {
        return filterIds;
    }

}
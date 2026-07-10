package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;

public class RepositoryRegistry {

    private final ProducerRepository producerRepository;

    private final PlaylistRepository playlistRepository;

    private final TrackRepository trackRepository;

    private final TransitionRepository transitionRepository;

    private final FilterRepository filterRepository;

    public RepositoryRegistry(BuildContext context) {

        this.producerRepository =
                new ProducerRepository(context);

        this.playlistRepository =
                new PlaylistRepository(context);

        this.trackRepository =
                new TrackRepository(context);

        this.transitionRepository =
                new TransitionRepository(context);

        this.filterRepository =
                new FilterRepository(context);

    }

    public ProducerRepository getProducerRepository() {
        return producerRepository;
    }

    public PlaylistRepository getPlaylistRepository() {
        return playlistRepository;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }

    public TransitionRepository getTransitionRepository() {
        return transitionRepository;
    }

    public FilterRepository getFilterRepository() {
        return filterRepository;
    }

}
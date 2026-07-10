package com.aivideostudio.kdenlive.builder.context;

import com.aivideostudio.kdenlive.builder.repository.RepositoryRegistry;
import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.kdenlive.model.Playlist;
import com.aivideostudio.kdenlive.model.Producer;
import com.aivideostudio.kdenlive.model.Track;
import com.aivideostudio.kdenlive.support.KdenliveIds;
import com.aivideostudio.pipeline.PipelineContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class BuildContext {

    /**
     * Input
     */
    private final PipelineContext pipelineContext;

    /**
     * Output
     */
    private final KdenliveProject project;

    /**
     * ID generators
     */
    private final KdenliveIds ids =
            new KdenliveIds();

    /**
     * Lookup cache
     */
    private final Map<String, Producer> producerIndex =
            new LinkedHashMap<>();

    private final Map<String, Playlist> playlistIndex =
            new LinkedHashMap<>();

    private final Map<String, Track> trackIndex =
            new LinkedHashMap<>();

    private final RepositoryRegistry repositories;

    public BuildContext(PipelineContext pipelineContext,
                        KdenliveProject project) {

        this.pipelineContext = pipelineContext;
        this.project = project;
        this.repositories = new RepositoryRegistry(this);
    }

    public PipelineContext getPipelineContext() {
        return pipelineContext;
    }

    public KdenliveProject getProject() {
        return project;
    }

    public KdenliveIds getIds() {
        return ids;
    }

    public Map<String, Producer> getProducerIndex() {
        return producerIndex;
    }

    public Map<String, Playlist> getPlaylistIndex() {
        return playlistIndex;
    }

    public Map<String, Track> getTrackIndex() {
        return trackIndex;
    }

    public RepositoryRegistry getRepositories() {
        return repositories;
    }

}
package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.builder.context.BuildContextFactory;
import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.pipeline.PipelineContext;

public class DefaultKdenliveBuilder
        implements KdenliveBuilder {

    private final ProducerBuilder producerBuilder;

    private final PlaylistBuilder playlistBuilder;

    private final TractorBuilder tractorBuilder;

    public DefaultKdenliveBuilder(
            ProducerBuilder producerBuilder,
            PlaylistBuilder playlistBuilder,
            TractorBuilder tractorBuilder) {

        this.producerBuilder = producerBuilder;
        this.playlistBuilder = playlistBuilder;
        this.tractorBuilder = tractorBuilder;

    }

    @Override
    public KdenliveProject build(PipelineContext pipelineContext) {

        KdenliveProject project = new KdenliveProject();

        project.setName(
                pipelineContext.getEpisode().getId());

        BuildContext buildContext =
                new BuildContextFactory()
                        .create(pipelineContext, project);

        producerBuilder.build(buildContext);

        playlistBuilder.build(buildContext);

        tractorBuilder.build(buildContext);

        return project;
    }

}
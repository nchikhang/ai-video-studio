package com.aivideostudio.kdenlive.builder.context;

import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.pipeline.PipelineContext;

public class BuildContextFactory {

    public BuildContext create(PipelineContext pipelineContext,
                               KdenliveProject project) {

        return new BuildContext(
                pipelineContext,
                project);

    }

}
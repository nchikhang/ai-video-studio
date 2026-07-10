package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.pipeline.PipelineContext;

public interface KdenliveBuilder {

    KdenliveProject build(
            PipelineContext context);

}
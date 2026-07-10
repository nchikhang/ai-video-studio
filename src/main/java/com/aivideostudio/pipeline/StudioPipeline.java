package com.aivideostudio.pipeline;

public interface StudioPipeline {

    void execute(PipelineContext context) throws Exception;

    default String getName() {
        return getClass().getSimpleName();
    }

}
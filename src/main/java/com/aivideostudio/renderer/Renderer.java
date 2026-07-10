package com.aivideostudio.renderer;

import com.aivideostudio.pipeline.PipelineContext;

public interface Renderer {

    void render(PipelineContext context) throws Exception;

}
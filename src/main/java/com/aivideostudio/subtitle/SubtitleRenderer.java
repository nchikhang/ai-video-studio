package com.aivideostudio.subtitle;

import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.renderer.Renderer;

public class SubtitleRenderer implements Renderer {

    private final SubtitleWriter writer;

    public SubtitleRenderer(SubtitleWriter writer) {
        this.writer = writer;
    }

    @Override
    public void render(PipelineContext context) throws Exception {

        writer.write(
                context.getWorkspace(),
                context.getTimeline()
        );
    }
}
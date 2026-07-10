package com.aivideostudio.renderer;

import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.preview.PreviewGenerator;

public class PreviewRenderer implements Renderer {

    private final PreviewGenerator previewGenerator;

    public PreviewRenderer(PreviewGenerator previewGenerator){
        this.previewGenerator=previewGenerator;
    }

    @Override
    public void render(PipelineContext context) throws Exception {
        previewGenerator.generate(
                context.getWorkspace(),
                context.getManifest()
        );
    }
}
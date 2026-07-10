package com.aivideostudio.pipeline.step;

import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.render.RenderManifest;
import com.aivideostudio.render.RenderManifestBuilder;

public class BuildManifestStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) {

        title("Building Manifest");

        RenderManifestBuilder builder =
                new RenderManifestBuilder();

        RenderManifest manifest =
                builder.build(
                        context.getTimeline());

        context.setManifest(manifest);

        success(
                "Render clips : "
                        + manifest.getClips().size());

    }

}
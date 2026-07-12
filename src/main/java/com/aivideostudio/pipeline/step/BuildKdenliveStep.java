package com.aivideostudio.pipeline.step;

import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.kdenlive.builder.*;
import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.kdenlive.writer.KdenliveXmlWriter;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

import java.nio.file.Path;

/**
 * Final build step: Timeline -> Kdenlive object model -> *.kdenlive on disk.
 */
public class BuildKdenliveStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {
        title("Building Kdenlive Project");

        KdenliveBuilder builder = new DefaultKdenliveBuilder(
                new DefaultProducerBuilder(),
                new DefaultPlaylistBuilder(),
                new DefaultTractorBuilder());

        KdenliveProject project = builder.build(context);

        Path output = context.getWorkspace()
                .getKdenliveFolder()
                .resolve(context.getEpisode().getId() + ".kdenlive");

        new KdenliveXmlWriter(ProjectPaths.ROOT.toString())
                .write(project, output);

        success("Kdenlive project : " + output);
    }
}
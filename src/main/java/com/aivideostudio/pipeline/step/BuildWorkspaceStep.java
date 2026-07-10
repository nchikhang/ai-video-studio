package com.aivideostudio.pipeline.step;

import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.workspace.EpisodeWorkspace;
import com.aivideostudio.workspace.WorkspaceBuilder;

public class BuildWorkspaceStep
        extends BasePipeline {

    @Override
    public void execute(
            PipelineContext context) {

        title("Building Workspace");

        WorkspaceBuilder builder =
                new WorkspaceBuilder();

        EpisodeWorkspace workspace =
                builder.build(
                        context.getEpisode());

        context.setWorkspace(workspace);

        success("Workspace created.");

    }

}
package com.aivideostudio.pipeline;

import java.util.ArrayList;
import java.util.List;

public class PipelineRunner {

    private final List<StudioPipeline> pipelines =
            new ArrayList<>();

    public PipelineRunner add(StudioPipeline pipeline) {

        pipelines.add(pipeline);

        return this;
    }

    public PipelineResult run(
            PipelineContext context) throws Exception {

        PipelineResult result =
                new PipelineResult();

        result.start();

        try {

            for (StudioPipeline pipeline : pipelines) {

                System.out.println();
                System.out.println(">> " + pipeline.getName());

                pipeline.execute(context);

            }

            result.finish();

        } catch (Exception ex) {

            result.fail();

            throw ex;

        }

        return result;

    }

}
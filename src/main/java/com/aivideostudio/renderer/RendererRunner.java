package com.aivideostudio.renderer;

import com.aivideostudio.pipeline.PipelineContext;

import java.util.ArrayList;
import java.util.List;

public class RendererRunner {

    private final List<Renderer> renderers =
            new ArrayList<>();

    public RendererRunner add(Renderer renderer){

        renderers.add(renderer);

        return this;

    }

    public RendererResult run(
            PipelineContext context) throws Exception {

        RendererResult result =
                new RendererResult();

        result.start();

        try{

            for(Renderer renderer : renderers){

                System.out.println();

                System.out.println(
                        ">> " +
                                renderer.getClass().getSimpleName());

                renderer.render(context);

            }

            result.finish();

        }catch (Exception ex){

            result.fail();

            throw ex;

        }

        return result;

    }

}
package com.aivideostudio.pipeline.step;

import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.episode.EpisodeLoader;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

public class LoadEpisodeStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {

        title("Loading Episode");

        EpisodeLoader loader = new EpisodeLoader();

        Episode episode =
                loader.load(
                        ProjectPaths.PROJECTS
                                .resolve("episode001")
                                .resolve("episode.yml")
                );

        context.setEpisode(episode);

        success("Episode : " + episode.getId());

        success("Scenes  : "
                + episode.getScenes().size());

    }

}
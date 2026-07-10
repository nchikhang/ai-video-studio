package com.aivideostudio.pipeline.step;

import com.aivideostudio.episode.EpisodeService;
import com.aivideostudio.episode.EpisodeValidator;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

public class ValidateEpisodeStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) {

        title("Validating Episode");

        EpisodeService service =
                new EpisodeService();

        service.inherit(context.getEpisode());

        EpisodeValidator validator =
                new EpisodeValidator(
                        context.getCharacterService(),
                        context.getAssets()
                );

        validator.validate(context.getEpisode());

        success("Episode validation passed.");

    }

}
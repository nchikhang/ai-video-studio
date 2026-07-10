package com.aivideostudio.pipeline.step;

import com.aivideostudio.character.CharacterLoader;
import com.aivideostudio.character.CharacterRegistry;
import com.aivideostudio.character.CharacterService;
import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

public class LoadCharacterStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {

        title("Loading Characters");

        CharacterLoader loader = new CharacterLoader();

        CharacterRegistry registry =
                loader.load(ProjectPaths.CHARACTER_CONFIG);

        CharacterService service =
                new CharacterService(registry);

        context.setCharacterService(service);

        success("Characters loaded.");
    }

}
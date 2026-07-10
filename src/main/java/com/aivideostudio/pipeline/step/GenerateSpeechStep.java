package com.aivideostudio.pipeline.step;

import com.aivideostudio.character.CharacterService;
import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.speech.SpeechTask;
import com.aivideostudio.speech.SpeechTaskBuilder;
import com.aivideostudio.tts.EdgeTTSService;

import java.nio.file.Path;
import java.util.List;

public class GenerateSpeechStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {

        title("Generating Speech");

        CharacterService characterService =
                context.getCharacterService();

        Path outputFolder =
                context.getWorkspace().getAudioFolder();

        SpeechTaskBuilder builder =
                new SpeechTaskBuilder(characterService);

        List<SpeechTask> tasks =
                builder.build(
                        context.getEpisode(),
                        outputFolder
                );

        context.setSpeechTasks(tasks);

        EdgeTTSService tts =
                new EdgeTTSService();

        tts.generate(tasks);

        success("Speech files : "
                + tasks.size());

    }

}
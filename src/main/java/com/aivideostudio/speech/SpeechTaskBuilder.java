package com.aivideostudio.speech;

import com.aivideostudio.character.CharacterService;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.episode.Scene;
import com.aivideostudio.util.OutputFileNaming;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SpeechTaskBuilder {

    private final CharacterService characterService;

    public SpeechTaskBuilder(CharacterService characterService) {
        this.characterService = characterService;
    }

    public List<SpeechTask> build(Episode episode, Path outputFolder) {

        List<SpeechTask> tasks = new ArrayList<>();

        for (Scene scene : episode.getScenes()) {

            SpeechTask task = new SpeechTask();

            task.setIndex(scene.getIndex());

            task.setCharacter(scene.getCharacter());

            task.setVoice(
                    characterService.voiceOf(scene.getCharacter())
            );

            task.setRate(
                    characterService.rateOf(scene.getCharacter())
            );

            task.setPitch(
                    characterService.pitchOf(scene.getCharacter())
            );

            task.setText(scene.getText());

            task.setOutputFile(OutputFileNaming.speech(outputFolder, task));

            tasks.add(task);

        }

        return tasks;

    }

}
package com.aivideostudio.timeline;

import com.aivideostudio.audio.AudioInfo;
import com.aivideostudio.audio.AudioMetadataReader;
import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.episode.Scene;
import com.aivideostudio.speech.SpeechTask;

import java.util.List;

public class TimelineBuilder {

    private final AudioMetadataReader reader;

    public TimelineBuilder(AudioMetadataReader reader) {

        this.reader = reader;

    }

    public Timeline build(Episode episode,
                          List<SpeechTask> tasks) {

        Timeline timeline = new Timeline();

        double current = 0;

        for (int i = 0; i < episode.getScenes().size(); i++) {

            Scene scene = episode.getScenes().get(i);

            SpeechTask task = tasks.get(i);

            TimelineClip clip = new TimelineClip();

            clip.setIndex(scene.getIndex());

            clip.setCharacter(scene.getCharacter());

            clip.setBackground(scene.getBackground());

            clip.setText(scene.getText());

            clip.setAudioFile(task.getOutputFile());

            clip.setStartTime(current);

            AudioInfo info = reader.read(task.getOutputFile());
            clip.setAudioInfo(info);
            clip.setDuration(info.getDurationSeconds());

            current += info.getDurationSeconds();

            clip.setEndTime(current);

            clip.setPose(scene.getPose());

            clip.setCharacterImage(
                    ProjectPaths.CHARACTERS
                            .resolve(scene.getCharacter())
                            .resolve(scene.getPose() + ".png")
            );

            clip.setBackgroundImage(
                    ProjectPaths.BACKGROUNDS
                            .resolve(scene.getBackground() + ".png")
            );

            timeline.add(clip);

        }

        return timeline;

    }

}
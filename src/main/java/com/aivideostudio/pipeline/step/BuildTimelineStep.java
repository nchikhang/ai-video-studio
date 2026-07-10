package com.aivideostudio.pipeline.step;

import com.aivideostudio.audio.AudioMetadataReader;
import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.speech.SpeechTask;
import com.aivideostudio.speech.SpeechTaskBuilder;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineBuilder;
import com.aivideostudio.timeline.TimelineClip;

import java.util.List;

public class BuildTimelineStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {

        title("Building Timeline");

        List<SpeechTask> tasks =
                context.getSpeechTasks();

        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalStateException(
                    "SpeechTask list is empty. GenerateSpeechStep must run first.");
        }

        info(
                "Speech tasks : " + tasks.size());

        TimelineBuilder timelineBuilder =
                new TimelineBuilder(
                        new AudioMetadataReader());

        Timeline timeline =
                timelineBuilder.build(
                        context.getEpisode(),
                        tasks);

        context.setTimeline(timeline);

        for (TimelineClip clip : timeline.getClips()) {

            System.out.printf(
                    "%03d | %.2f -> %.2f | %s%n",
                    clip.getIndex(),
                    clip.getStartTime(),
                    clip.getEndTime(),
                    clip.getText());

        }

    }

}
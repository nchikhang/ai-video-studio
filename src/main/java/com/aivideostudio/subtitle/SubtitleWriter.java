package com.aivideostudio.subtitle;

import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.timeline.TimelineClip;
import com.aivideostudio.workspace.EpisodeWorkspace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SubtitleWriter {

    public void write(EpisodeWorkspace workspace,
                      Timeline timeline) throws IOException {

        Path outputFolder = workspace.getSubtitleFolder().resolve("subtitle.srt");
        Files.createDirectories(outputFolder);

        Path file = outputFolder.resolve("subtitle.srt");

        StringBuilder builder = new StringBuilder();

        int index = 1;

        for (TimelineClip clip : timeline.getClips()) {

            builder.append(index++)
                    .append("\n");

            builder.append(TimeFormatter.format(clip.getStartTime()))
                    .append(" --> ")
                    .append(TimeFormatter.format(clip.getEndTime()))
                    .append("\n");

            builder.append(clip.getText())
                    .append("\n\n");
        }

        Files.writeString(file, builder.toString());

        System.out.println("Subtitle : " + file);
    }
}
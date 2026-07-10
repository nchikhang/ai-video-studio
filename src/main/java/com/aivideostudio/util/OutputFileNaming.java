package com.aivideostudio.util;

import com.aivideostudio.speech.SpeechTask;

import java.nio.file.Path;

public class OutputFileNaming {
    public static Path speech(Path folder, SpeechTask task) {
        return folder.resolve(
                "%03d_%s.mp3".formatted(
                        task.getIndex(),
                        task.getCharacter()
                )
        );
    }

    public static Path subtitle(Path folder) {
        return folder.resolve("subtitle.srt");
    }

    public static Path timeline(Path folder) {
        return folder.resolve("timeline.json");
    }
}

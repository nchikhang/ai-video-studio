package com.aivideostudio.tts;

import com.aivideostudio.speech.SpeechTask;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EdgeTTSService {
    public void generate(List<SpeechTask> tasks) throws Exception {
        for (SpeechTask task : tasks) {
            System.out.println("Generating : " + task.getOutputFile().getFileName());
            generate(task);
        }
    }

    public void generate(SpeechTask task) throws Exception {
        Files.createDirectories(task.getOutputFile().getParent());
        generate(task.getVoice(), task.getRate(), task.getPitch(), task.getText(), task.getOutputFile());
    }

    public void generate(String voice, String rate, String pitch, String text, Path outputFile) throws Exception {
        List<String> cmd = new ArrayList<>();
        cmd.add("edge-tts");
        cmd.add("--voice");
        cmd.add(voice);
        cmd.add("--rate=" + rate);
        cmd.add("--pitch=" + pitch);
        cmd.add("--text");
        cmd.add(text);
        cmd.add("--write-media");
        cmd.add(outputFile.toAbsolutePath().toString());

        System.out.println();
        System.out.println("Generating : " + outputFile.getFileName());
        System.out.println(String.join(" ", cmd));

        int code = CommandExecutor.execute(cmd);
        if (code != 0) {
            throw new RuntimeException("edge-tts failed : " + code);
        }
    }
}
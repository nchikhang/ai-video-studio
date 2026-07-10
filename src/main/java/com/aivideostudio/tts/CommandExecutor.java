package com.aivideostudio.tts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class CommandExecutor {

    public static int execute(List<String> command) throws Exception {

        ProcessBuilder pb = new ProcessBuilder(command);

        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

        }

        return process.waitFor();

    }

}
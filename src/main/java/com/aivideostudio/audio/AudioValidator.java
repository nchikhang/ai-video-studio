package com.aivideostudio.audio;

import java.nio.file.Files;
import java.nio.file.Path;

public class AudioValidator {

    public void validate(Path file) {

        if (!Files.exists(file)) {

            throw new AudioMetadataException(
                    "Audio not found : " + file);

        }

    }

}
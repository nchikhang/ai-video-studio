package com.aivideostudio.audio;

import com.mpatric.mp3agic.Mp3File;

import java.nio.file.Files;
import java.nio.file.Path;

public class AudioMetadataReader {

    public AudioInfo read(Path file) {

        try {

            if (!Files.exists(file)) {
                throw new AudioMetadataException(
                        "Audio file not found : " + file);
            }

            Mp3File mp3 = new Mp3File(file.toFile());

            AudioInfo info = new AudioInfo();

            info.setDurationSeconds(mp3.getLengthInSeconds());

            info.setSampleRate(mp3.getSampleRate());

            info.setBitrate(mp3.getBitrate());

            info.setFileSize(Files.size(file));

            return info;

        } catch (Exception ex) {

            throw new AudioMetadataException(
                    "Cannot read audio metadata : " + file,
                    ex);

        }

    }

}
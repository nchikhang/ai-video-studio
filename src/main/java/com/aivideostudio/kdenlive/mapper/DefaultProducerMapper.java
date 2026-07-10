package com.aivideostudio.kdenlive.mapper;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Producer;
import com.aivideostudio.kdenlive.model.ProducerType;

public class DefaultProducerMapper
        implements ProducerMapper {

    @Override
    public Producer map(BuildContext context,
                        String resource) {

        ProducerType type = detect(resource);

        switch (type) {

            case IMAGE:
                return context.getRepositories()
                        .getProducerRepository()
                        .createImage(resource);

            case AUDIO:
                return context.getRepositories()
                        .getProducerRepository()
                        .createAudio(resource, 0);

            case VIDEO:
                return context.getRepositories()
                        .getProducerRepository()
                        .createVideo(resource, 0);

            default:
                throw new IllegalArgumentException(
                        "Unsupported resource : " + resource);
        }
    }

    private ProducerType detect(String resource) {

        String lower = resource.toLowerCase();

        if (lower.endsWith(".png")
                || lower.endsWith(".jpg")
                || lower.endsWith(".jpeg")
                || lower.endsWith(".webp")) {

            return ProducerType.IMAGE;
        }

        if (lower.endsWith(".mp3")
                || lower.endsWith(".wav")
                || lower.endsWith(".ogg")) {

            return ProducerType.AUDIO;
        }

        if (lower.endsWith(".mp4")
                || lower.endsWith(".mov")
                || lower.endsWith(".mkv")) {

            return ProducerType.VIDEO;
        }

        throw new IllegalArgumentException(
                "Unknown resource : " + resource);
    }
}
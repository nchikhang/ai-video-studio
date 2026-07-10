package com.aivideostudio.kdenlive.builder.repository;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Producer;
import com.aivideostudio.kdenlive.model.ProducerType;

import java.util.Collection;

public class ProducerRepository {

    private final BuildContext context;

    public ProducerRepository(BuildContext context) {
        this.context = context;
    }

    public Producer create(ProducerType type,
                           String resource,
                           long duration) {

        Producer existing = findByResource(resource);

        if (existing != null) {
            return existing;
        }

        Producer producer = new Producer();

        producer.setId(
                context.getIds()
                        .producers()
                        .next());

        producer.setType(type);

        producer.setResource(resource);

        producer.setDuration(duration);

        context.getProject()
                .getProducers()
                .add(producer);

        context.getProducerIndex()
                .put(producer.getId(), producer);

        return producer;
    }

    public Producer find(String id) {

        return context.getProducerIndex()
                .get(id);

    }

    public Producer findByResource(String resource) {

        Collection<Producer> values =
                context.getProducerIndex().values();

        for (Producer producer : values) {

            if (resource.equals(
                    producer.getResource())) {

                return producer;

            }

        }

        return null;

    }

    public Collection<Producer> findAll() {

        return context.getProducerIndex().values();

    }

    public Producer createImage(String resource) {

        return create(
                ProducerType.IMAGE,
                resource,
                0
        );

    }

    public Producer createAudio(String resource,
                                long duration) {

        return create(
                ProducerType.AUDIO,
                resource,
                duration
        );

    }

    public Producer createVideo(String resource,
                                long duration) {

        return create(
                ProducerType.VIDEO,
                resource,
                duration
        );

    }
}
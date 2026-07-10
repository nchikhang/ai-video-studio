package com.aivideostudio.kdenlive.model;

public class ProducerFactory {

    private final ProducerIdGenerator generator =
            new ProducerIdGenerator();

    public Producer image(String file) {

        Producer producer = new Producer();

        producer.setId(generator.next());

        producer.setType(ProducerType.IMAGE);

        producer.setResource(file);

        return producer;

    }

    public Producer audio(String file) {

        Producer producer = new Producer();

        producer.setId(generator.next());

        producer.setType(ProducerType.AUDIO);

        producer.setResource(file);

        return producer;

    }

}
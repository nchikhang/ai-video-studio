package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;

public class EmptyProducerBuilder
        implements ProducerBuilder {

    @Override
    public void build(BuildContext context) {

        context.getRepositories()
                .getProducerRepository()
                .createImage(
                        "assets/backgrounds/classroom.png"
                );

        context.getRepositories()
                .getProducerRepository()
                .createAudio(
                        "output/episode001/001_teddy.mp3",
                        3200
                );

        context.getRepositories()
                .getProducerRepository()
                .createImage(
                        "assets/backgrounds/classroom.png"
                );

        System.out.println(
                "Producer count = "
                        + context.getProject()
                        .getProducers()
                        .size());

    }

}
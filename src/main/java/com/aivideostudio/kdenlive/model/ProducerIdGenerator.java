package com.aivideostudio.kdenlive.model;

public class ProducerIdGenerator {

    private int sequence = 0;

    public String next() {

        return "producer" + sequence++;

    }

}
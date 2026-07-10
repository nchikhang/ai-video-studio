package com.aivideostudio.kdenlive.support;

public class IdGenerator {

    private final String prefix;

    private int sequence;

    public IdGenerator(String prefix) {
        this.prefix = prefix;
    }

    public synchronized String next() {
        return prefix + sequence++;
    }

    public void reset() {
        sequence = 0;
    }

    public int current() {
        return sequence;
    }

}
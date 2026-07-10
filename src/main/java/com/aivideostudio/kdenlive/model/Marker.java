package com.aivideostudio.kdenlive.model;

public class Marker {

    private String id;

    private String name;

    private long frame;

    public Marker() {
    }

    public Marker(String id,
                  String name,
                  long frame) {
        this.id = id;
        this.name = name;
        this.frame = frame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFrame() {
        return frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Marker{" +
                "frame=" + frame +
                ", name='" + name + '\'' +
                '}';
    }
}
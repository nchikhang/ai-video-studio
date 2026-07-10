package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private String id;

    private TransitionType type;

    /**
     * Source track index.
     */
    private String aTrack;

    /**
     * Destination track index.
     */
    private String bTrack;

    /**
     * Start frame.
     */
    private long inFrame;

    /**
     * End frame.
     */
    private long outFrame;

    private final List<Property> properties =
            new ArrayList<>();

    public Transition() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransitionType getType() {
        return type;
    }

    public void setType(TransitionType type) {
        this.type = type;
    }

    public String getaTrack() {
        return aTrack;
    }

    public void setaTrack(String aTrack) {
        this.aTrack = aTrack;
    }

    public String getbTrack() {
        return bTrack;
    }

    public void setbTrack(String bTrack) {
        this.bTrack = bTrack;
    }

    public long getInFrame() {
        return inFrame;
    }

    public void setInFrame(long inFrame) {
        this.inFrame = inFrame;
    }

    public long getOutFrame() {
        return outFrame;
    }

    public void setOutFrame(long outFrame) {
        this.outFrame = outFrame;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {

        return "Transition{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", aTrack=" + aTrack +
                ", bTrack=" + bTrack +
                '}';

    }

}
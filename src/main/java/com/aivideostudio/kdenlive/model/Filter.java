package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    private String id;

    /**
     * MLT service
     * ex: volume, brightness, affine...
     */
    private String service;

    private long inFrame;

    private long outFrame;

    private final List<Property> properties =
            new ArrayList<>();

    public Filter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

        return "Filter{" +
                "service='" + service + '\'' +
                '}';

    }

}
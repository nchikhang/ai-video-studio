package com.aivideostudio.kdenlive.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Producer {

    private String id;

    private ProducerType type;

    private String resource;

    private String name;

    /**
     * milliseconds
     */
    private long duration;

    private final Map<String, String> properties =
            new LinkedHashMap<>();

    public Producer() {
    }

    public Producer(String id,
                    ProducerType type,
                    String resource) {

        this.id = id;
        this.type = type;
        this.resource = resource;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProducerType getType() {
        return type;
    }

    public void setType(ProducerType type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {

        return "Producer{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", resource='" + resource + '\'' +
                '}';

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
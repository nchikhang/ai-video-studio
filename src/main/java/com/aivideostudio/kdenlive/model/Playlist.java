package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String id;

    private String name;

    private final List<PlaylistEntry> entries =
            new ArrayList<>();

    private final List<Property> properties =
            new ArrayList<>();

    public Playlist() {
    }

    public Playlist(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaylistEntry> getEntries() {
        return entries;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {

        return "Playlist{" +
                "id='" + id + '\'' +
                ", entries=" + entries.size() +
                '}';

    }

}
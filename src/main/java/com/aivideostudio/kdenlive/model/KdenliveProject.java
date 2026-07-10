package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KdenliveProject {

    private String name;

    private Profile profile = new Profile();

    private final List<Producer> producers = new ArrayList<>();

    private final List<Playlist> playlists = new ArrayList<>();

    private Tractor tractor;

    private final List<Marker> markers = new ArrayList<>();

    private final Map<String, String> properties = new LinkedHashMap<>();

    public KdenliveProject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Tractor getTractor() {
        return tractor;
    }

    public void setTractor(Tractor tractor) {
        this.tractor = tractor;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "KdenliveProject{" +
                "name='" + name + '\'' +
                ", producers=" + producers.size() +
                ", playlists=" + playlists.size() +
                '}';
    }

}
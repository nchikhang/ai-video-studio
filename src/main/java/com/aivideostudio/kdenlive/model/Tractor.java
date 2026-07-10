package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.List;

public class Tractor {

    private String id = "tractor0";

    private final List<Track> tracks =
            new ArrayList<>();

    private final List<Transition> transitions =
            new ArrayList<>();

    private final List<Filter> filters =
            new ArrayList<>();

    private final List<Property> properties =
            new ArrayList<>();

    public Tractor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {

        return "Tractor{" +
                "tracks=" + tracks.size() +
                ", transitions=" + transitions.size() +
                ", filters=" + filters.size() +
                '}';

    }

}
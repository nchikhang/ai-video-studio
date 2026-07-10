package com.aivideostudio.kdenlive.model;

import java.util.ArrayList;
import java.util.List;

public class Track {

    private String id;

    /**
     * playlist id
     */
    private String playlistId;

    private TrackType type;

    private boolean mute;

    private boolean hidden;

    private boolean locked;

    private int index;

    private final List<Property> properties =
            new ArrayList<>();

    public Track() {
    }

    public Track(String id,
                 String playlistId,
                 TrackType type) {

        this.id = id;
        this.playlistId = playlistId;
        this.type = type;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {

        return "Track{" +
                "id='" + id + '\'' +
                ", playlistId='" + playlistId + '\'' +
                ", type=" + type +
                '}';

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
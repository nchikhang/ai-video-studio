package com.aivideostudio.composition;

import java.nio.file.Path;

public class CompositionClip {

    private TrackType track;

    private Path file;

    private double start;

    private double duration;

    private double end;

    private int layer;

    public TrackType getTrack() {
        return track;
    }

    public void setTrack(TrackType track) {
        this.track = track;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

}
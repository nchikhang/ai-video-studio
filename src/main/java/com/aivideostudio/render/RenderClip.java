package com.aivideostudio.render;

import java.nio.file.Path;

public class RenderClip {

    private int index;

    private String character;

    private String pose;

    private Path background;

    private Path characterImage;

    private Path audio;

    private String text;

    private double start;

    private double duration;

    private double end;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPose() {
        return pose;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }

    public Path getBackground() {
        return background;
    }

    public void setBackground(Path background) {
        this.background = background;
    }

    public Path getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Path characterImage) {
        this.characterImage = characterImage;
    }

    public Path getAudio() {
        return audio;
    }

    public void setAudio(Path audio) {
        this.audio = audio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

}
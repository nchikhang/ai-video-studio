package com.aivideostudio.kdenlive.model;

public class Profile {

    private String description = "HD 1080p 30fps";

    private int width = 1920;

    private int height = 1080;

    private double fps = 30.0;

    private boolean progressive = true;

    private int sampleAspectNum = 1;

    private int sampleAspectDen = 1;

    private int displayAspectNum = 16;

    private int displayAspectDen = 9;

    public Profile() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public boolean isProgressive() {
        return progressive;
    }

    public void setProgressive(boolean progressive) {
        this.progressive = progressive;
    }

    public int getSampleAspectNum() {
        return sampleAspectNum;
    }

    public void setSampleAspectNum(int sampleAspectNum) {
        this.sampleAspectNum = sampleAspectNum;
    }

    public int getSampleAspectDen() {
        return sampleAspectDen;
    }

    public void setSampleAspectDen(int sampleAspectDen) {
        this.sampleAspectDen = sampleAspectDen;
    }

    public int getDisplayAspectNum() {
        return displayAspectNum;
    }

    public void setDisplayAspectNum(int displayAspectNum) {
        this.displayAspectNum = displayAspectNum;
    }

    public int getDisplayAspectDen() {
        return displayAspectDen;
    }

    public void setDisplayAspectDen(int displayAspectDen) {
        this.displayAspectDen = displayAspectDen;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "width=" + width +
                ", height=" + height +
                ", fps=" + fps +
                '}';
    }
}
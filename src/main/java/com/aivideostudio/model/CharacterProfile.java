package com.aivideostudio.model;

public class CharacterProfile {

    private String id;

    private String displayName;

    private String voice;

    private String rate;

    private String pitch;

    private String imageFolder;

    private String defaultPose;

    private String subtitleColor;

    private String defaultAnimation;

    public CharacterProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
        this.imageFolder = imageFolder;
    }

    public String getDefaultPose() {
        return defaultPose;
    }

    public void setDefaultPose(String defaultPose) {
        this.defaultPose = defaultPose;
    }

    public String getSubtitleColor() {
        return subtitleColor;
    }

    public void setSubtitleColor(String subtitleColor) {
        this.subtitleColor = subtitleColor;
    }

    public String getDefaultAnimation() {
        return defaultAnimation;
    }

    public void setDefaultAnimation(String defaultAnimation) {
        this.defaultAnimation = defaultAnimation;
    }
}
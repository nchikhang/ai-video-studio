package com.aivideostudio.timeline;

import com.aivideostudio.audio.AudioInfo;

import java.nio.file.Path;

public class TimelineClip {
    private int index;
    private String character;
    private String background;
    private String text;
    private Path audioFile;
    private double startTime;
    private double duration;
    private double endTime;
    private AudioInfo audioInfo;
    private String pose;
    private Path characterImage;
    private Path backgroundImage;
    private String animation;
    private String transition;
    private double x;
    private double y;
    private double scale = 1.0;
    private Path propImage;
    private Path cardImage;

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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Path getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(Path audioFile) {
        this.audioFile = audioFile;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public String getPose() {
        return pose;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }

    public Path getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Path characterImage) {
        this.characterImage = characterImage;
    }

    public Path getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Path backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Path getPropImage() {
        return propImage;
    }

    public void setPropImage(Path propImage) {
        this.propImage = propImage;
    }

    public Path getCardImage() {
        return cardImage;
    }

    public void setCardImage(Path cardImage) {
        this.cardImage = cardImage;
    }
}
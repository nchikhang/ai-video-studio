package com.aivideostudio.episode;

public class Scene {

    private int index;

    private String background;

    private String character;

    private String pose;

    private String text;

    private String prop;

    private String card;

    public Scene() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "index=" + index +
                ", background='" + background + '\'' +
                ", character='" + character + '\'' +
                ", pose='" + pose + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
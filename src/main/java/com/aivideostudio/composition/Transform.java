package com.aivideostudio.composition;

/** Transform values for one layer/asset. Fields nullable so per-asset overrides can be partial. */
public class Transform {

    private Double x;      // normalized center 0..1
    private Double y;
    private Double scale;  // fraction of frame
    private String animation; // "static" | "pop"

    public Transform() {}

    public Transform(Double x, Double y, Double scale, String animation) {
        this.x = x; this.y = y; this.scale = scale; this.animation = animation;
    }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Double getScale() { return scale; }
    public void setScale(Double scale) { this.scale = scale; }
    public String getAnimation() { return animation; }
    public void setAnimation(String animation) { this.animation = animation; }
}

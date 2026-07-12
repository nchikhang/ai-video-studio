package com.aivideostudio.composition;

import com.aivideostudio.kdenlive.model.Filter;
import com.aivideostudio.kdenlive.model.Profile;
import com.aivideostudio.kdenlive.model.Property;

import java.util.Locale;

/**
 * v0.9 — Automatic Video Composer.
 *
 * Turns a layer role (background / character / hero / card) into a Kdenlive
 * `qtblend` transform filter with a `rect` keyframe string, so each layer is
 * auto-positioned and animated (hero pops in). Coordinates are normalized:
 * cx,cy in [0,1] = center of the clip in the frame; scale = fraction of the
 * frame the clip occupies.
 */
public class Composer {

    private static final int POP_FRAMES = 9; // ~0.3s at 30fps

    private final Profile profile;

    public Composer(Profile profile) {
        this.profile = profile;
    }

    // ---- layer presets -------------------------------------------------
    public Filter background() {                 // full frame, static
        return still(0.5, 0.5, 1.0);
    }

    public Filter character() {                  // docked bottom-left
        return still(0.24, 0.66, 0.55);
    }

    public Filter hero() {                        // center, pops in
        return popIn(0.5, 0.5, 0.5);
    }

    public Filter card() {                        // top-center, static
        return still(0.5, 0.12, 0.34);
    }

    /** Honour explicit values from a TimelineClip when the composer defaults aren't wanted. */
    public Filter custom(double cx, double cy, double scale, String animation) {
        if ("pop".equalsIgnoreCase(animation)) return popIn(cx, cy, scale);
        return still(cx, cy, scale);
    }

    /** Static volume reduction for a continuous background-music track. */
    public Filter musicVolume() {
        Filter f = new Filter();
        f.setService("volume");
        f.getProperties().add(new Property("gain", "0.18")); // ~ -15 dB under the VO
        return f;
    }

    // ---- builders ------------------------------------------------------
    private Filter still(double cx, double cy, double scale) {
        return qtblend(key(0) + "=" + rect(cx, cy, scale, 1.0));
    }

    private Filter popIn(double cx, double cy, double scale) {
        String kf = key(0) + "=" + rect(cx, cy, scale * 0.05, 0.0)
                + ";" + key(POP_FRAMES) + "=" + rect(cx, cy, scale, 1.0);
        return qtblend(kf);
    }

    private Filter qtblend(String rectValue) {
        Filter f = new Filter();
        f.setService("qtblend");
        add(f, "rotate_center", "1");
        add(f, "kdenlive_id", "qtblend");
        add(f, "rect", rectValue);
        add(f, "rotation", key(0) + "=0");
        add(f, "compositing", "0");
        add(f, "distort", "0");
        return f;
    }

    private void add(Filter f, String name, String value) {
        f.getProperties().add(new Property(name, value));
    }

    private String rect(double cx, double cy, double scale, double opacity) {
        int W = profile.getWidth(), H = profile.getHeight();
        long w = Math.round(W * scale);
        long h = Math.round(H * scale);
        long x = Math.round(cx * W - w / 2.0);
        long y = Math.round(cy * H - h / 2.0);
        return x + " " + y + " " + w + " " + h + " "
                + String.format(Locale.US, "%.6f", opacity);
    }

    /** Keyframe key as Kdenlive timecode HH:MM:SS.mmm (relative to clip start). */
    private String key(long frame) {
        long ms = Math.round(frame / profile.getFps() * 1000.0);
        long hh = ms / 3_600_000; ms %= 3_600_000;
        long mm = ms / 60_000;    ms %= 60_000;
        long ss = ms / 1000;      ms %= 1000;
        return String.format(Locale.US, "%02d:%02d:%02d.%03d", hh, mm, ss, ms);
    }
}
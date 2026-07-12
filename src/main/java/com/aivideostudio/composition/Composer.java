package com.aivideostudio.composition;

import com.aivideostudio.kdenlive.model.Filter;
import com.aivideostudio.kdenlive.model.Profile;
import com.aivideostudio.kdenlive.model.Property;

import java.util.Locale;

/**
 * v0.9 — Automatic Video Composer.
 *
 * Builds a Kdenlive `qtblend` transform for a layer/asset using values from
 * {@link CompositionConfig} (assets/config/composition.yml): per-layer defaults,
 * overridable per asset. Coordinates normalized: cx,cy in [0,1] = clip center in
 * the frame; scale = fraction of the frame occupied.
 */
public class Composer {

    private static final int POP_FRAMES = 9; // ~0.3s at 30fps

    private final Profile profile;
    private final CompositionConfig config;

    public Composer(Profile profile, CompositionConfig config) {
        this.profile = profile;
        this.config = config != null ? config : CompositionConfig.defaults();
    }

    /** Transform for a layer ("background"/"character"/"hero"/"card") + asset key (nullable). */
    public Filter transform(String layer, String assetKey) {
        Transform t = config.resolve(layer, assetKey);
        double x = t.getX(), y = t.getY(), s = t.getScale();
        if ("pop".equalsIgnoreCase(t.getAnimation())) return popIn(x, y, s);
        return still(x, y, s);
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

    private String key(long frame) {
        long ms = Math.round(frame / profile.getFps() * 1000.0);
        long hh = ms / 3_600_000; ms %= 3_600_000;
        long mm = ms / 60_000;    ms %= 60_000;
        long ss = ms / 1000;      ms %= 1000;
        return String.format(Locale.US, "%02d:%02d:%02d.%03d", hh, mm, ss, ms);
    }
}
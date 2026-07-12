package com.aivideostudio.composition;

import java.util.HashMap;
import java.util.Map;

/**
 * Transform configuration: per-layer defaults + per-asset overrides.
 * Loaded from assets/config/composition.yml (see CompositionConfigLoader), or the
 * built-in {@link #defaults()} when no file is present.
 */
public class CompositionConfig {

    private final Map<String, Transform> defaults = new HashMap<>();
    private final Map<String, Transform> assets = new HashMap<>();

    public Map<String, Transform> getDefaults() { return defaults; }
    public Map<String, Transform> getAssets() { return assets; }

    /** Merge layer default with an optional per-asset override (override wins field-by-field). */
    public Transform resolve(String layer, String assetKey) {
        Transform base = defaults.getOrDefault(layer, fallback(layer));
        Transform ov = assetKey == null ? null : assets.get(assetKey);
        double x = pick(ov == null ? null : ov.getX(), base.getX(), 0.5);
        double y = pick(ov == null ? null : ov.getY(), base.getY(), 0.5);
        double s = pick(ov == null ? null : ov.getScale(), base.getScale(), 1.0);
        String a = ov != null && ov.getAnimation() != null ? ov.getAnimation()
                : (base.getAnimation() != null ? base.getAnimation() : "static");
        return new Transform(x, y, s, a);
    }

    private double pick(Double a, Double b, double fallback) {
        if (a != null) return a;
        if (b != null) return b;
        return fallback;
    }

    private Transform fallback(String layer) {
        return switch (layer) {
            case "character" -> new Transform(0.24, 0.70, 0.42, "static");
            case "hero"      -> new Transform(0.5, 0.5, 0.5, "pop");
            case "card"      -> new Transform(0.5, 0.12, 0.34, "static");
            default          -> new Transform(0.5, 0.5, 1.0, "static"); // background
        };
    }

    /** Built-in defaults, used when composition.yml is absent. */
    public static CompositionConfig defaults() {
        CompositionConfig c = new CompositionConfig();
        c.defaults.put("background", new Transform(0.5, 0.5, 1.0, "static"));
        c.defaults.put("character", new Transform(0.24, 0.70, 0.42, "static"));
        c.defaults.put("hero", new Transform(0.5, 0.5, 0.5, "pop"));
        c.defaults.put("card", new Transform(0.5, 0.12, 0.34, "static"));
        return c;
    }
}

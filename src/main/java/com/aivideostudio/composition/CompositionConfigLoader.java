package com.aivideostudio.composition;

import com.aivideostudio.loader.YamlLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Loads composition.yml into a {@link CompositionConfig}. Falls back to built-in
 * defaults when the file is missing. Kept separate from CompositionConfig so the
 * builders depend only on the POJO (no YAML dependency).
 *
 * composition.yml:
 *   defaults:
 *     character: { x: 0.24, y: 0.70, scale: 0.42, animation: static }
 *   assets:
 *     teddy: { scale: 0.40 }
 *     ball:  { scale: 0.30 }
 */
public class CompositionConfigLoader {

    private final YamlLoader yaml = new YamlLoader();

    @SuppressWarnings("unchecked")
    public CompositionConfig load(Path file) {
        if (file == null || !Files.exists(file)) {
            return CompositionConfig.defaults();
        }
        try {
            Map<String, Object> root = yaml.load(file);
            CompositionConfig cfg = CompositionConfig.defaults(); // seed with defaults
            if (root == null) return cfg;
            readSection((Map<String, Object>) root.get("defaults"), cfg.getDefaults());
            readSection((Map<String, Object>) root.get("assets"), cfg.getAssets());
            return cfg;
        } catch (Exception e) {
            System.err.println("composition.yml load failed, using defaults: " + e.getMessage());
            return CompositionConfig.defaults();
        }
    }

    @SuppressWarnings("unchecked")
    private void readSection(Map<String, Object> section, Map<String, Transform> target) {
        if (section == null) return;
        for (Map.Entry<String, Object> e : section.entrySet()) {
            Map<String, Object> m = (Map<String, Object>) e.getValue();
            if (m == null) continue;
            Transform t = new Transform();
            t.setX(toDouble(m.get("x")));
            t.setY(toDouble(m.get("y")));
            t.setScale(toDouble(m.get("scale")));
            Object anim = m.get("animation");
            if (anim != null) t.setAnimation(anim.toString());
            target.put(e.getKey(), t);
        }
    }

    private Double toDouble(Object o) {
        if (o == null) return null;
        if (o instanceof Number n) return n.doubleValue();
        return Double.parseDouble(o.toString());
    }
}

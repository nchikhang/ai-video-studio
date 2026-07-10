package com.aivideostudio.asset;

import com.aivideostudio.model.Asset;

import java.util.LinkedHashMap;
import java.util.Map;

public class AssetService {
    private final Map<String, Asset> backgrounds = new LinkedHashMap<>();

    public void addBackground(Asset asset) {
        backgrounds.put(asset.getId(), asset);
    }

    public Asset getBackground(String id) {
        return backgrounds.get(id);
    }

    public Map<String, Asset> getBackgrounds() {
        return backgrounds;
    }
}

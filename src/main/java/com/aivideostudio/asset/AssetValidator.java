package com.aivideostudio.asset;

import com.aivideostudio.model.Asset;
import java.nio.file.Files;

public class AssetValidator {
    public void validate(AssetService service) {
        for (Asset asset : service.getBackgrounds().values()) {
            if (!Files.exists(asset.getFile())) {
                throw new IllegalStateException( "Missing background: " + asset.getFile());
            }
        }
    }
}

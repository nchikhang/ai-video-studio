package com.aivideostudio.asset;

import com.aivideostudio.model.Asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AssetScanner {
    public AssetService scan() throws IOException {
        AssetService service = new AssetService();
        scanBackgrounds(service);
        return service;
    }

    private void scanBackgrounds(AssetService service) throws IOException {
        Path dir = Paths.get("assets/backgrounds");
        if (!Files.exists(dir)) {
            System.out.println("Background folder not found.");
            return;
        }

        try (Stream<Path> stream = Files.list(dir)) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                String fileName = path.getFileName().toString();
                int dot = fileName.lastIndexOf('.');
                String id = dot > 0 ? fileName.substring(0, dot) : fileName;
                service.addBackground( new Asset(id, path));
            });
        }
    }
}

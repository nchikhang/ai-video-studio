package com.aivideostudio.workspace;

import java.nio.file.Path;

public class EpisodeWorkspace {

    private final Path root;

    public EpisodeWorkspace(Path root) {
        this.root = root;
    }

    public Path getRoot() {
        return root;
    }

    public Path getAudioFolder() {
        return root.resolve("audio");
    }

    public Path getSubtitleFolder() {
        return root.resolve("subtitle");
    }

    public Path getPreviewFolder() {
        return root.resolve("preview");
    }

    public Path getManifestFolder() {
        return root.resolve("manifest");
    }

    public Path getKdenliveFolder() {
        return root.resolve("kdenlive");
    }

    public Path getThumbnailFolder() {
        return root.resolve("thumbnail");
    }

}
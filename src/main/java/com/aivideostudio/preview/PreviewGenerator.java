package com.aivideostudio.preview;

import com.aivideostudio.render.RenderManifest;
import com.aivideostudio.workspace.EpisodeWorkspace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PreviewGenerator {

    private final PreviewPageBuilder pageBuilder =
            new PreviewPageBuilder();

    public void generate(EpisodeWorkspace workspace,
                         RenderManifest manifest)
            throws IOException {

        Files.createDirectories(workspace.getPreviewFolder());

        String html =
                pageBuilder.build(manifest);

        Files.writeString(
                workspace.getPreviewFolder().resolve("preview.html"),
                html
        );

    }

}
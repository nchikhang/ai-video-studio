package com.aivideostudio.render;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenderManifestWriter {

    private final ObjectMapper mapper;

    public RenderManifestWriter() {

        mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

    }

    public void write(Path file,
                      RenderManifest manifest)
            throws IOException {

        Files.createDirectories(file.getParent());

        mapper.writeValue(file.toFile(), manifest);

    }

}
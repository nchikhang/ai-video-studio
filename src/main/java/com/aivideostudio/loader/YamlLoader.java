package com.aivideostudio.loader;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YamlLoader {

    @SuppressWarnings("unchecked")
    public Map<String, Object> load(Path file) throws IOException {

        Yaml yaml = new Yaml();

        try (InputStream in = Files.newInputStream(file)) {
            return yaml.load(in);
        }

    }

}
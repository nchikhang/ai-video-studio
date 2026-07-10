package com.aivideostudio.loader;

import com.aivideostudio.episode.Episode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonLoader() {
    }

    public static Episode loadEpisode(String file) throws IOException {

        return MAPPER.readValue(new File(file), Episode.class);

    }

}
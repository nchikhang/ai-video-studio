package com.aivideostudio.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ProjectPaths {

    public static final Path ROOT =
            Paths.get("").toAbsolutePath();

    public static final Path ASSETS =
            ROOT.resolve("assets");

    public static final Path CONFIG =
            ASSETS.resolve("config");

    public static final Path BACKGROUNDS =
            ASSETS.resolve("backgrounds");

    public static final Path CHARACTERS =
            ASSETS.resolve("characters");

    public static final Path MUSIC =
            ASSETS.resolve("music");

    public static final Path EFFECTS =
            ASSETS.resolve("effects");

    public static final Path PROPS =
            ASSETS.resolve("props");

    public static final Path CARDS =
            ASSETS.resolve("cards");

    public static final Path PROJECTS =
            ROOT.resolve("projects");

    public static final Path OUTPUT =
            ROOT.resolve("output");

    public static final Path CHARACTER_CONFIG =
            CONFIG.resolve("characters.yml");

    public static final Path BACKGROUND_CONFIG =
            CONFIG.resolve("backgrounds.yml");

    private ProjectPaths() {
    }

}
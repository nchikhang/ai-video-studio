package com.aivideostudio.model;

import java.nio.file.Path;

public class Asset {

    private String id;

    private Path file;

    public Asset() {
    }

    public Asset(String id, Path file) {
        this.id = id;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public Path getFile() {
        return file;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return id + " -> " + file;
    }
}
package com.aivideostudio.episode;

public class EpisodeService {

    public void inherit(Episode episode) {

        String currentBackground = null;

        for (Scene scene : episode.getScenes()) {

            if (scene.getBackground() == null) {
                scene.setBackground(currentBackground);
            } else {
                currentBackground = scene.getBackground();
            }

        }

    }

}
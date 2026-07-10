package com.aivideostudio.episode;

import com.aivideostudio.asset.AssetService;
import com.aivideostudio.character.CharacterService;

public class EpisodeValidator {

    private final CharacterService characterService;
    private final AssetService assetService;

    public EpisodeValidator(CharacterService characterService,
                            AssetService assetService) {
        this.characterService = characterService;
        this.assetService = assetService;
    }

    public void validate(Episode episode) {

        for (Scene scene : episode.getScenes()) {

            if (!characterService.exists(scene.getCharacter())) {
                throw new RuntimeException(
                        "Scene " + scene.getIndex()
                                + " : Character not found : "
                                + scene.getCharacter());
            }

            if (scene.getBackground() != null &&
                    !assetService.getBackgrounds()
                            .containsKey(scene.getBackground())) {

                throw new RuntimeException(
                        "Scene " + scene.getIndex()
                                + " : Background not found : "
                                + scene.getBackground());

            }

            if (scene.getText() == null ||
                    scene.getText().isBlank()) {

                throw new RuntimeException(
                        "Scene " + scene.getIndex()
                                + " : Empty dialog.");

            }

        }

    }

}
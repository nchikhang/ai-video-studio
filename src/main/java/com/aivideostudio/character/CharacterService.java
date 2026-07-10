package com.aivideostudio.character;

import com.aivideostudio.model.CharacterProfile;

import java.util.Collection;

public class CharacterService {

    private final CharacterRegistry registry;

    public CharacterService(CharacterRegistry registry) {
        this.registry = registry;
    }

    public CharacterProfile find(String id) {

        CharacterProfile profile = registry.find(id);

        if (profile == null) {
            throw new IllegalArgumentException(
                    "Character not found : " + id);
        }

        return profile;

    }

    public boolean exists(String id) {
        return registry.exists(id);
    }

    public Collection<CharacterProfile> list() {
        return registry.list();
    }

    public String voiceOf(String id) {
        return find(id).getVoice();
    }

    public String rateOf(String id) {
        return find(id).getRate();
    }

    public String pitchOf(String id) {
        return find(id).getPitch();
    }

    public String defaultPoseOf(String id) {
        return find(id).getDefaultPose();
    }

    public String imageFolderOf(String id) {
        return find(id).getImageFolder();
    }

    public String subtitleColorOf(String id) {
        return find(id).getSubtitleColor();
    }

    public String animationOf(String id) {
        return find(id).getDefaultAnimation();
    }

}
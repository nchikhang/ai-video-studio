package com.aivideostudio.character;

import com.aivideostudio.model.CharacterProfile;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterRegistry {

    private final Map<String, CharacterProfile> characters = new LinkedHashMap<>();

    public void register(CharacterProfile profile) {

        if (profile == null) {
            throw new IllegalArgumentException("Character profile is null.");
        }

        if (profile.getId() == null || profile.getId().isBlank()) {
            throw new IllegalArgumentException("Character id is empty.");
        }

        characters.put(profile.getId(), profile);
    }

    public CharacterProfile find(String id) {
        return characters.get(id);
    }

    public boolean exists(String id) {
        return characters.containsKey(id);
    }

    public Collection<CharacterProfile> list() {
        return characters.values();
    }

    public int size() {
        return characters.size();
    }

    public void clear() {
        characters.clear();
    }

}
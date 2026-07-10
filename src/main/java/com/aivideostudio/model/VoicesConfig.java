package com.aivideostudio.model;

import java.util.HashMap;
import java.util.Map;

public class VoicesConfig {

    private Map<String, VoiceProfile> voices = new HashMap<>();

    public VoicesConfig() {
    }

    public Map<String, VoiceProfile> getVoices() {
        return voices;
    }

    public void setVoices(Map<String, VoiceProfile> voices) {
        this.voices = voices;
    }
}
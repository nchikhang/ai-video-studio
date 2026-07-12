package com.aivideostudio.pipeline;

import com.aivideostudio.composition.CompositionConfig;

import com.aivideostudio.asset.AssetService;
import com.aivideostudio.character.CharacterService;
import com.aivideostudio.episode.Episode;
import com.aivideostudio.render.RenderManifest;
import com.aivideostudio.speech.SpeechTask;
import com.aivideostudio.timeline.Timeline;
import com.aivideostudio.workspace.EpisodeWorkspace;

import java.util.List;

public class PipelineContext {

    private CompositionConfig compositionConfig = CompositionConfig.defaults();


    private AssetService assets;

    private CharacterService characterService;

    private Episode episode;

    private Timeline timeline;

    private RenderManifest manifest;

    private List<SpeechTask> speechTasks;

    private EpisodeWorkspace workspace;

    public AssetService getAssets() {
        return assets;
    }

    public void setAssets(AssetService assets) {
        this.assets = assets;
    }

    public CharacterService getCharacterService() {
        return characterService;
    }

    public void setCharacterService(CharacterService characterService) {
        this.characterService = characterService;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public RenderManifest getManifest() {
        return manifest;
    }

    public void setManifest(RenderManifest manifest) {
        this.manifest = manifest;
    }

    public List<SpeechTask> getSpeechTasks() {
        return speechTasks;
    }

    public void setSpeechTasks(List<SpeechTask> speechTasks) {
        this.speechTasks = speechTasks;
    }

    public EpisodeWorkspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(EpisodeWorkspace workspace) {
        this.workspace = workspace;
    }

    public CompositionConfig getCompositionConfig() {
        return compositionConfig;
    }

    public void setCompositionConfig(CompositionConfig compositionConfig) {
        this.compositionConfig = compositionConfig;
    }
}

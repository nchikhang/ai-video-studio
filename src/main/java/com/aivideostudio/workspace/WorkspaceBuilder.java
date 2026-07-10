package com.aivideostudio.workspace;

import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.episode.Episode;

public class WorkspaceBuilder {

    public EpisodeWorkspace build(Episode episode){

        return new EpisodeWorkspace(

                ProjectPaths.OUTPUT
                        .resolve(episode.getId())

        );

    }

}
package com.aivideostudio.pipeline.step;

import com.aivideostudio.composition.CompositionConfig;
import com.aivideostudio.composition.CompositionConfigLoader;
import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

/** Loads assets/config/composition.yml into the context (defaults if the file is absent). */
public class LoadCompositionConfigStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) {
        title("Loading Composition Config");
        CompositionConfig cfg = new CompositionConfigLoader()
                .load(ProjectPaths.CONFIG.resolve("composition.yml"));
        context.setCompositionConfig(cfg);
        success("Composition config loaded (" + cfg.getAssets().size() + " asset overrides)");
    }
}

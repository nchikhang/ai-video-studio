package com.aivideostudio.pipeline.step;

import com.aivideostudio.config.ProjectPaths;
import com.aivideostudio.kdenlive.builder.*;
import com.aivideostudio.kdenlive.model.KdenliveProject;
import com.aivideostudio.kdenlive.writer.KdenliveXmlWriter;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

import java.nio.file.Path;

/**
 * v1.0 — headless render via MLT `melt`.
 *
 * Writes a render-mode MLT (root producer = timeline tractor, NOT the bin — otherwise
 * melt would render the project bin instead of the timeline) and encodes it to mp4.
 * Produces &lt;id&gt;_raw.mp4; loudness/format normalization happens in PublishCheckStep.
 */
public class RenderStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {
        title("Rendering Video (melt)");

        KdenliveProject project = new DefaultKdenliveBuilder(
                new DefaultProducerBuilder(),
                new DefaultPlaylistBuilder(),
                new DefaultTractorBuilder()).build(context);

        String id = context.getEpisode().getId();
        Path renderMlt = context.getWorkspace().getKdenliveFolder().resolve(id + "_render.kdenlive");
        Path rawMp4 = context.getWorkspace().getRoot().resolve(id + "_raw.mp4");

        new KdenliveXmlWriter(ProjectPaths.ROOT.toString())
                .write(project, renderMlt, true);   // renderMode = true
        info("Render MLT: " + renderMlt);

        int code = run(
                "melt", renderMlt.toString(),
                "-consumer", "avformat:" + rawMp4,
                "vcodec=libx264", "preset=medium", "crf=20",
                "acodec=aac", "ab=192k", "ar=48000");
        if (code != 0) {
            throw new IllegalStateException("melt render failed (exit " + code + "). Is melt installed?");
        }
        success("Rendered: " + rawMp4);
    }

    private int run(String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        try (var r = p.inputReader()) {
            String line;
            while ((line = r.readLine()) != null) { /* drain (melt is chatty) */ }
        }
        return p.waitFor();
    }
}

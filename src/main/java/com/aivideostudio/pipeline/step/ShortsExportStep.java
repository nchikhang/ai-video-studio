package com.aivideostudio.pipeline.step;

import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.timeline.TimelineClip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * v1.1 — YouTube Shorts export (9:16).
 *
 * Cuts one vertical clip per scene from the published video and reframes 16:9 -> 1080x1920
 * with a blurred fill (the whole frame stays visible; nothing is cropped out). Output:
 * output/&lt;id&gt;/shorts/&lt;id&gt;_short_NN.mp4.
 */
public class ShortsExportStep extends BasePipeline {

    private static final String REFRAME =
            "[0:v]split=2[blur][main];" +
            "[blur]scale=1080:1920:force_original_aspect_ratio=increase," +
                    "crop=1080:1920,boxblur=20:5[bg];" +
            "[main]scale=1080:1920:force_original_aspect_ratio=decrease[fg];" +
            "[bg][fg]overlay=(W-w)/2:(H-h)/2,setsar=1[v]";

    @Override
    public void execute(PipelineContext context) throws Exception {
        title("Exporting Shorts (9:16)");

        if (context.getTimeline() == null || context.getTimeline().getClips().isEmpty()) {
            info("No timeline scenes; skipping Shorts export.");
            return;
        }

        String id = context.getEpisode().getId();
        Path root = context.getWorkspace().getRoot();
        Path source = root.resolve(id + ".mp4");                 // published (normalized)
        if (!Files.exists(source)) source = root.resolve(id + "_raw.mp4"); // fallback
        if (!Files.exists(source)) {
            info("\u26a0 No rendered video found (" + id + ".mp4); run RenderStep first.");
            return;
        }

        Path shortsDir = root.resolve("shorts");
        Files.createDirectories(shortsDir);

        List<TimelineClip> clips = context.getTimeline().getClips();
        int made = 0;
        for (int i = 0; i < clips.size(); i++) {
            TimelineClip clip = clips.get(i);
            Path out = shortsDir.resolve(
                    String.format(Locale.US, "%s_short_%02d.mp4", id, i + 1));
            int code = run(
                    "ffmpeg", "-y", "-v", "error",
                    "-ss", fmt(clip.getStartTime()),
                    "-t", fmt(clip.getDuration()),
                    "-i", source.toString(),
                    "-filter_complex", REFRAME,
                    "-map", "[v]", "-map", "0:a?",
                    "-c:v", "libx264", "-crf", "20", "-preset", "medium",
                    "-c:a", "aac", "-b:a", "192k", "-ar", "48000",
                    out.toString());
            if (code == 0) { made++; info("Short " + (i + 1) + " -> " + out.getFileName()); }
            else info("\u26a0 Short " + (i + 1) + " failed (ffmpeg exit " + code + ")");
        }
        success(made + "/" + clips.size() + " Shorts exported -> " + shortsDir);
    }

    private String fmt(double seconds) {
        return String.format(Locale.US, "%.3f", seconds);
    }

    private int run(String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        try (var r = p.inputReader()) { while (r.readLine() != null) {} }
        return p.waitFor();
    }
}

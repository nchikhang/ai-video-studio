package com.aivideostudio.pipeline.step;

import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * v1.0 — publish gate (ffmpeg): 2-pass loudnorm to -14 LUFS / 48 kHz, then blackdetect,
 * silencedetect (fail if any valley > 0.8s), and a thumbnail. Loudness is normalized in
 * place (&lt;id&gt;.mp4); black/silence issues are reported as warnings for review.
 */
public class PublishCheckStep extends BasePipeline {

    private static final double SILENCE_MAX = 0.8; // seconds

    @Override
    public void execute(PipelineContext context) throws Exception {
        title("Publish Check (ffmpeg)");
        String id = context.getEpisode().getId();
        Path root = context.getWorkspace().getRoot();
        Path raw = root.resolve(id + "_raw.mp4");
        Path out = root.resolve(id + ".mp4");
        Path thumb = context.getWorkspace().getThumbnailFolder().resolve(id + ".jpg");

        normalizeLoudness(raw, out);          // 2-pass loudnorm -> -14 LUFS, 48 kHz
        checkBlack(out);
        checkSilence(out);
        makeThumbnail(out, thumb);

        success("Publish check done -> " + out);
    }

    // -- loudnorm 2-pass --------------------------------------------------
    private void normalizeLoudness(Path in, Path out) throws Exception {
        info("Loudness pass 1/2 (measuring)...");
        String log = capture(
                "ffmpeg", "-hide_banner", "-nostats", "-i", in.toString(),
                "-af", "loudnorm=I=-14:TP=-1:LRA=11:print_format=json",
                "-f", "null", "-");
        String json = lastJsonBlock(log);
        String mI = field(json, "input_i"), mTP = field(json, "input_tp"),
               mLRA = field(json, "input_lra"), mThr = field(json, "input_thresh"),
               off = field(json, "target_offset");

        List<String> cmd = new ArrayList<>(List.of(
                "ffmpeg", "-hide_banner", "-y", "-i", in.toString()));
        String af = "loudnorm=I=-14:TP=-1:LRA=11:print_format=summary";
        if (mI != null && !mI.contains("inf")) {   // valid measurement -> linear 2nd pass
            af += ":measured_I=" + mI + ":measured_TP=" + mTP
                + ":measured_LRA=" + mLRA + ":measured_thresh=" + mThr
                + ":offset=" + off + ":linear=true";
        }
        cmd.addAll(List.of("-af", af, "-ar", "48000", "-c:v", "copy",
                "-c:a", "aac", "-b:a", "192k", out.toString()));
        info("Loudness pass 2/2 (applying -14 LUFS / 48 kHz)...");
        if (run(cmd.toArray(new String[0])) != 0) {
            throw new IllegalStateException("loudnorm pass 2 failed");
        }
    }

    // -- black frames -----------------------------------------------------
    private void checkBlack(Path file) throws Exception {
        String log = capture("ffmpeg", "-hide_banner", "-nostats", "-i", file.toString(),
                "-vf", "blackdetect=d=0.1:pic_th=0.98", "-an", "-f", "null", "-");
        List<String> hits = matches(log, "black_start:([0-9.]+)");
        if (hits.isEmpty()) info("blackdetect: OK (no black frames)");
        else info("\u26a0 blackdetect: " + hits.size() + " black segment(s) at " + hits);
    }

    // -- silence (valleys) ------------------------------------------------
    private void checkSilence(Path file) throws Exception {
        String log = capture("ffmpeg", "-hide_banner", "-nostats", "-i", file.toString(),
                "-af", "silencedetect=noise=-50dB:d=" + SILENCE_MAX, "-f", "null", "-");
        List<String> durs = matches(log, "silence_duration: ([0-9.]+)");
        if (durs.isEmpty()) info("silencedetect: OK (no valley > " + SILENCE_MAX + "s)");
        else info("\u26a0 silencedetect: " + durs.size() + " valley(ies) > "
                + SILENCE_MAX + "s, durations=" + durs);
    }

    // -- thumbnail --------------------------------------------------------
    private void makeThumbnail(Path file, Path thumb) throws Exception {
        java.nio.file.Files.createDirectories(thumb.getParent());
        if (run("ffmpeg", "-y", "-v", "error", "-ss", "1", "-i", file.toString(),
                "-frames:v", "1", "-q:v", "2", thumb.toString()) == 0) {
            info("thumbnail: " + thumb);
        } else {
            info("\u26a0 thumbnail extraction failed");
        }
    }

    // -- helpers ----------------------------------------------------------
    private String lastJsonBlock(String s) {
        Matcher m = Pattern.compile("\\{[^{}]*\\}", Pattern.DOTALL).matcher(s);
        String last = "{}";
        while (m.find()) last = m.group();
        return last;
    }

    private String field(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        return m.find() ? m.group(1) : null;
    }

    private List<String> matches(String s, String regex) {
        List<String> out = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) out.add(m.group(1));
        return out;
    }

    private String capture(String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        StringBuilder sb = new StringBuilder();
        try (var r = p.inputReader()) {
            String line;
            while ((line = r.readLine()) != null) sb.append(line).append('\n');
        }
        p.waitFor();
        return sb.toString();
    }

    private int run(String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        try (var r = p.inputReader()) { while (r.readLine() != null) {} }
        return p.waitFor();
    }
}

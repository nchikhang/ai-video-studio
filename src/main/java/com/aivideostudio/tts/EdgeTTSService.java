package com.aivideostudio.tts;

import com.aivideostudio.speech.SpeechTask;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Bản vá: tự thử lại khi edge-tts lỗi mạng (DNS/timeout) + bỏ qua clip đã tạo.
 * Thay thế trực tiếp file cũ. Không đổi API, không ảnh hưởng các step khác.
 */
public class EdgeTTSService {

    private static final int MAX_RETRIES = 4;   // số lần thử mỗi clip
    private static final long BASE_WAIT_MS = 1500L; // backoff: 1.5s, 3s, 4.5s...

    public void generate(List<SpeechTask> tasks) throws Exception {
        for (SpeechTask task : tasks) {
            generate(task);
        }
    }

    public void generate(SpeechTask task) throws Exception {
        Files.createDirectories(task.getOutputFile().getParent());
        generate(task.getVoice(), task.getRate(), task.getPitch(), task.getText(), task.getOutputFile());
    }

    public void generate(String voice, String rate, String pitch, String text, Path outputFile) throws Exception {

        // Resume: nếu clip đã tồn tại và không rỗng thì bỏ qua.
        if (Files.exists(outputFile) && Files.size(outputFile) > 0) {
            System.out.println("Skip (đã có) : " + outputFile.getFileName());
            return;
        }

        List<String> cmd = new ArrayList<>();
        cmd.add("edge-tts");
        cmd.add("--voice");
        cmd.add(voice);
        cmd.add("--rate=" + rate);
        cmd.add("--pitch=" + pitch);
        cmd.add("--text");
        cmd.add(text);
        cmd.add("--write-media");
        cmd.add(outputFile.toAbsolutePath().toString());

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            System.out.println();
            System.out.println("Generating : " + outputFile.getFileName()
                    + (attempt > 1 ? "  (thử lại " + attempt + "/" + MAX_RETRIES + ")" : ""));
            System.out.println(String.join(" ", cmd));

            int code = CommandExecutor.execute(cmd);

            // Thành công = exit 0 VÀ file có dữ liệu thật.
            if (code == 0 && Files.exists(outputFile) && Files.size(outputFile) > 0) {
                return;
            }

            // Dọn file rỗng/hỏng trước khi thử lại.
            Files.deleteIfExists(outputFile);

            if (attempt < MAX_RETRIES) {
                long waitMs = BASE_WAIT_MS * attempt;
                System.out.println("edge-tts lỗi (code " + code + "). Chờ " + waitMs + "ms rồi thử lại...");
                Thread.sleep(waitMs);
            }
        }

        throw new RuntimeException("edge-tts thất bại sau " + MAX_RETRIES
                + " lần thử : " + outputFile.getFileName());
    }
}

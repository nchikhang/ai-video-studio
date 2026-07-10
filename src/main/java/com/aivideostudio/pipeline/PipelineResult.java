package com.aivideostudio.pipeline;

import java.time.Duration;
import java.time.Instant;

public class PipelineResult {

    private Instant start;

    private Instant end;

    private boolean success;

    public void start() {
        start = Instant.now();
    }

    public void finish() {
        end = Instant.now();
        success = true;
    }

    public void fail() {
        end = Instant.now();
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public Duration getDuration() {

        if (start == null || end == null) {
            return Duration.ZERO;
        }

        return Duration.between(start, end);
    }

}
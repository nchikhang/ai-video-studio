package com.aivideostudio.kdenlive.support;

public final class FrameUtil {

    private FrameUtil() {
    }

    public static long millisToFrame(long millis,
                                     double fps) {

        return Math.round(
                millis * fps / 1000.0
        );

    }

    public static long secondsToFrame(double seconds,
                                      double fps) {

        return Math.round(
                seconds * fps
        );

    }

    public static double frameToSeconds(long frame,
                                        double fps) {

        return frame / fps;

    }

}
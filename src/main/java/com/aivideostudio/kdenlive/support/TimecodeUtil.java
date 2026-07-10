package com.aivideostudio.kdenlive.support;

public final class TimecodeUtil {

    private TimecodeUtil() {
    }

    public static String toTimecode(long frame,
                                    double fps) {

        long totalSeconds =
                (long) (frame / fps);

        long ff =
                frame - Math.round(totalSeconds * fps);

        long hh = totalSeconds / 3600;

        long mm = (totalSeconds % 3600) / 60;

        long ss = totalSeconds % 60;

        return String.format(
                "%02d:%02d:%02d:%02d",
                hh,
                mm,
                ss,
                ff
        );

    }

}
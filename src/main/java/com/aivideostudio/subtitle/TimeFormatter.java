package com.aivideostudio.subtitle;

public final class TimeFormatter {

    private TimeFormatter() {
    }

    public static String format(double seconds) {

        long total = (long) (seconds * 1000);

        long hour = total / 3600000;

        total %= 3600000;

        long minute = total / 60000;

        total %= 60000;

        long second = total / 1000;

        long milli = total % 1000;

        return String.format(
                "%02d:%02d:%02d,%03d",
                hour,
                minute,
                second,
                milli
        );

    }

}
package blizzard.development.time.utils;

public class TimeConverter {
    public static String convertSecondsToTimeFormat(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        StringBuilder timeFormat = new StringBuilder();

        if (days > 0) {
            timeFormat.append(days).append(" dia").append(days > 1 ? "s" : "");
        }
        if (hours > 0) {
            if (!timeFormat.isEmpty()) timeFormat.append(", ");
            timeFormat.append(hours).append(" hora").append(hours > 1 ? "s" : "");
        }
        if (minutes > 0) {
            if (!timeFormat.isEmpty()) timeFormat.append(", ");
            timeFormat.append(minutes).append(" minuto").append(minutes > 1 ? "s" : "");
        }
        if (remainingSeconds > 0 || timeFormat.isEmpty()) {
            if (!timeFormat.isEmpty()) timeFormat.append(" e ");
            timeFormat.append(remainingSeconds).append(" segundo").append(remainingSeconds > 1 ? "s" : "");
        }

        return timeFormat.toString();
    }
}


package blizzard.development.vips.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
    public static long parseTime(String time) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("(\\d+)([dhms])");
        Matcher matcher = pattern.matcher(time);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Formato de tempo inválido! Use números seguidos de d, h, m ou s.");
        }

        long value = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch (unit) {
            case "d" -> value * 86400;
            case "h" -> value * 3600;
            case "m" -> value * 60;
            case "s" -> value;
            default -> throw new IllegalArgumentException("Unidade de tempo inválida! Use: d, h, m ou s.");
        };
    }
}

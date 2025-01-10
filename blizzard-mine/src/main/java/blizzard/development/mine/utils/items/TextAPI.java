package blizzard.development.mine.utils.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAPI {

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)");
    private static final Pattern BOLD_PATTERN = Pattern.compile("<bold>(.*?)</bold>");

    public static Component parse(String input) {
        Component result = Component.empty();
        int lastEnd = 0;

        Matcher boldMatcher = BOLD_PATTERN.matcher(input);
        while (boldMatcher.find()) {
            if (lastEnd < boldMatcher.start()) {
                result = result.append(parseColor(input.substring(lastEnd, boldMatcher.start())));
            }
            String boldText = boldMatcher.group(1);
            result = result.append(parseColor(boldText).decoration(TextDecoration.BOLD, true));
            lastEnd = boldMatcher.end();
        }

        if (lastEnd < input.length()) {
            result = result.append(parseColor(input.substring(lastEnd)));
        }

        return result.decoration(TextDecoration.ITALIC, false);
    }

    private static Component parseColor(String input) {
        Matcher matcher = GRADIENT_PATTERN.matcher(input);
        Component result = Component.empty();
        int lastIndex = 0;

        while (matcher.find()) {
            if (lastIndex < matcher.start()) {
                result = result.append(Component.text(input.substring(lastIndex, matcher.start()))
                    .decoration(TextDecoration.ITALIC, false));
            }

            Color startColor = Color.decode("#" + matcher.group(1));
            String text = matcher.group(2);
            Color endColor = Color.decode("#" + matcher.group(3));
            result = result.append(applyGradient(text, startColor, endColor));
            lastIndex = matcher.end();
        }

        if (lastIndex < input.length()) {
            result = result.append(parseHexColor(input.substring(lastIndex))
                .decoration(TextDecoration.ITALIC, false));
        }

        return result;
    }

    private static Component parseHexColor(String input) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(input);
        Component result = Component.empty();
        int lastIndex = 0;

        while (matcher.find()) {
            if (lastIndex < matcher.start()) {
                result = result.append(Component.text(input.substring(lastIndex, matcher.start()))
                    .decoration(TextDecoration.ITALIC, false));
            }

            String colorHex = matcher.group(1);
            TextColor color = TextColor.fromHexString("#" + colorHex);
            String text = matcher.group(2);

            result = result.append(Component.text(text)
                .color(color)
                .decoration(TextDecoration.ITALIC, false));
            lastIndex = matcher.end();
        }

        if (lastIndex < input.length()) {
            result = result.append(Component.text(input.substring(lastIndex))
                .decoration(TextDecoration.ITALIC, false));
        }

        return result;
    }

    private static Component applyGradient(String text, Color startColor, Color endColor) {
        int length = text.length();
        Component result = Component.empty();

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1);
            int red = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
            int green = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
            int blue = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);

            Color color = new Color(red, green, blue);
            TextColor textColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
            result = result.append(Component.text(String.valueOf(text.charAt(i)))
                .color(textColor)
                .decoration(TextDecoration.ITALIC, false));
        }
        return result;
    }
}

package blizzard.development.clans.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LPerms {
    private static final net.luckperms.api.LuckPerms luckPerms = LPermsRegistry.getLuckPerms();

    public static Component getPrefix(Player player) {
        if (luckPerms == null) return null;

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            QueryOptions queryOptions = luckPerms.getContextManager().getStaticQueryOptions();
            String prefix = user.getCachedData().getMetaData(queryOptions).getPrefix();
            if (prefix != null) {
                prefix = color(prefix);
                return convertColorCodes(prefix);
            }
        }
        return null;
    }

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
    private static final Pattern BOLD_PATTERN = Pattern.compile("<bold>(.*?)</bold>");

    public static Component parseGradient(String input) {
        input = input.replace("<bold>", "").replace("</bold>", "");

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
            result = result.append(Component.text(input.substring(lastIndex))
                    .decoration(TextDecoration.ITALIC, false));
        }

        Matcher boldMatcher = BOLD_PATTERN.matcher(input);
        if (boldMatcher.find()) {
            String boldText = boldMatcher.group(1);
            result = result.decorate(TextDecoration.BOLD);
        }

        return result.decoration(TextDecoration.ITALIC, false);
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

    public static String color(String message) {
        return message.replace("&", "§");
    }

    public static Component convertColorCodes(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }
}

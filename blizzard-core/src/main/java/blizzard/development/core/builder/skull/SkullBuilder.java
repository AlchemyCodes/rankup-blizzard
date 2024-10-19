package blizzard.development.core.builder.skull;

import blizzard.development.core.utils.items.SkullAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SkullBuilder {
    private final ItemStack item;

    public SkullBuilder(String base64) {
        this.item = new ItemStack(SkullAPI.fromBase64(SkullAPI.Type.ITEM, base64));
    }

    public SkullBuilder name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(parseGradient(name, false));
        item.setItemMeta(meta);
        return this;
    }

    public SkullBuilder lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<Component> loreComponents = Arrays.stream(lore)
                .map(line -> parseGradient(line, false))
                .collect(Collectors.toList());
        meta.lore(loreComponents);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return item;
    }

    private static Component parseGradient(String input, boolean bold) {
        Pattern pattern = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
        Matcher matcher = pattern.matcher(input);
        Component result = Component.empty();

        int lastIndex = 0;
        while (matcher.find()) {
            if (lastIndex < matcher.start()) {
                result = result.append(Component.text(input.substring(lastIndex, matcher.start()))
                        .decoration(TextDecoration.BOLD, bold)
                        .decoration(TextDecoration.ITALIC, false));
            }
            Color startColor = Color.decode("#" + matcher.group(1));
            String text = matcher.group(2);
            Color endColor = Color.decode("#" + matcher.group(3));
            result = result.append(applyGradient(text, startColor, endColor, bold));
            lastIndex = matcher.end();
        }

        if (lastIndex < input.length()) {
            result = result.append(Component.text(input.substring(lastIndex))
                    .decoration(TextDecoration.BOLD, bold)
                    .decoration(TextDecoration.ITALIC, false));
        }

        return result.decoration(TextDecoration.ITALIC, false);
    }

    private static Component applyGradient(String text, Color startColor, Color endColor, boolean bold) {
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
                    .decoration(TextDecoration.BOLD, bold)
                    .decoration(TextDecoration.ITALIC, false));
        }
        return result;
    }
}

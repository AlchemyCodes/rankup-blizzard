package blizzard.development.bosses.utils.gradient;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemGradientUtils {

    public static ItemStack createCustomItem(String itemName, Material material, List<String> lore, boolean nameBold, boolean loreBold, int customModelData) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.displayName(parseGradient(itemName, nameBold));
            List<Component> loreComponents = new ArrayList<>();
            for(String line : lore) {
                loreComponents.add(parseGradient(line, loreBold));
            }
            meta.lore(loreComponents);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if(customModelData != -1) {
                meta.setCustomModelData(customModelData);
            }

            item.setItemMeta(meta);
        }

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

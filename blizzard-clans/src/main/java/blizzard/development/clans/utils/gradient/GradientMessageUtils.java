package blizzard.development.clans.utils.gradient;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.awt.*;

public class GradientMessageUtils {
    public static void sendGradientMessage(Player player, String message, String startHexColor, String endHexColor) {
        Component gradientMessage = applyGradient(message, startHexColor, endHexColor);
        player.sendMessage(gradientMessage);
    }

    public static Component applyGradient(String text, String startHexColor, String endHexColor) {
        Color startColor = Color.decode(startHexColor);
        Color endColor = Color.decode(endHexColor);

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

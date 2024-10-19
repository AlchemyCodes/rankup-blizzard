package blizzard.development.bosses.utils.commands;

import blizzard.development.bosses.utils.items.apis.TextAPI;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PermissionChecker {
    public static Boolean check(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            Location location = player.getLocation();
            Sound sound = Sound.ENTITY_VILLAGER_NO;
            player.playSound(location, sound, 0.5F, 0.5F);
            String message = "§cVocê não tem permissão para isso!";
            player.sendActionBar(TextAPI.parse(message));
            return false;
        }
    }
}

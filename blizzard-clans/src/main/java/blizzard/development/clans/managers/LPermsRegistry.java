package blizzard.development.clans.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;

public class LPermsRegistry {
    private static LuckPerms luckPerms = null;

    public static void register() {
        if (Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            System.out.println("LuckPerms not found.");
            return;
        }

        luckPerms = LuckPermsProvider.get();

    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
}

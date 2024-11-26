package blizzard.development.core.api;

import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.managers.CampfireManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CoreAPI {
    private static CoreAPI instance;
    private static boolean isBlizzard;

    public boolean isIsBlizzard() {
        return isBlizzard;
    }

    public void setBlizzardTrue() {
        isBlizzard = true;
    }

    public void setBlizzardFalse() {
        isBlizzard = false;
    }

    public void startBlizzard() {
        CoreAPI instance = CoreAPI.getInstance();

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            world.setStorm(true);
            instance.setBlizzardTrue();
            player.sendTitle("§b§lO FRIO CHEGOU.", "§fProteja-se, a tempestade de neve chegou.", 10, 70, 20);
        }
    }

    public void stopBlizzard() {
        CoreAPI instance = CoreAPI.getInstance();

        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            world.setStorm(false);
            instance.setBlizzardFalse();
            CampfireManager.removeCampfire(player);
            player.sendTitle("§b§lA TEMPESTADE PASSOU.", "§fAproveite a calmaria antes que ela volte.", 10, 70, 20);
        }
    }

    public double getTemperature(Player player) {
        return PlayersCacheManager.getTemperature(player);
    }

    public String getPlayerClothing(Player player) {
        return PlayersCacheManager.getPlayerClothing(player);
    }

    public static CoreAPI getInstance() {
        if (instance == null) {
            instance = new CoreAPI();
        }
        return instance;
    }
}

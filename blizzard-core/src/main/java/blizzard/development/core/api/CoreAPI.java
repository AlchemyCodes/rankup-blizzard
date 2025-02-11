package blizzard.development.core.api;

import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.managers.GeneratorManager;
import blizzard.development.core.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
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
            GeneratorManager.getInstance().deactivateGenerator(player);
            player.sendTitle("§b§lA TEMPESTADE PASSOU.", "§fAproveite a calmaria antes que ela volte.", 10, 70, 20);
        }
    }

    public double getTemperature(Player player) {
        return PlayersCacheManager.getInstance().getTemperature(player);
    }

    public String getPlayerClothing(Player player) {
        return PlayersCacheManager.getInstance().getPlayerClothing(player);
    }

    public boolean isFreezing(Player player) {
        return getTemperature(player) <= 0;
    }

    public boolean hasAnyClothing(Player player) {
        switch (getPlayerClothing(player)) {
            case "COMMON", "RARE", "MYSTIC", "LEGENDARY" -> {
                return true;
            }
        }
        return false;
    }

    public boolean hasGeneratorOn(Player player) {
        return GeneratorManager.getInstance().hasGenerator(player);
    }

    public double getPlayerBonus(Player player) {
        String clothing = PlayersCacheManager.getInstance().getPlayerClothing(player);
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

        switch (clothing) {
            case "COMMON" -> {
                return 1 + (double) config.getInt("clothes.common.percentage") / 100;
            }
            case "RARE" -> {
                return 1 + (double) config.getInt("clothes.rare.percentage") / 100;
            }
            case "MYSTIC" -> {
                return 1 + (double) config.getInt("clothes.mystic.percentage") / 100;
            }
            case "LEGENDARY" -> {
                return 1 + (double) config.getInt("clothes.legendary.percentage") / 100;
            }
        }
        return 1;
    }

    public static CoreAPI getInstance() {
        if (instance == null) {
            instance = new CoreAPI();
        }
        return instance;
    }
}

package blizzard.development.fishing.utils.fish;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FishesUtils {

    public static int getStrengthNecessary(String rarity) {
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("strengthNeeded.common");
            case "rare" -> config.getInt("strengthNeeded.rare");
            case "legendary" -> config.getInt("strengthNeeded.legendary");
            case "mystic" -> config.getInt("strengthNeeded.mystic");
            default -> 0;
        };
    }

    public static String getRarity(int playerStrength) {
        Random random = new Random();
        int chance = random.nextInt(101);

        if (playerStrength >= getStrengthNecessary("mystic")) {
            if (chance <= 50) return "common";
            else if (chance <= 80) return "rare";
            else if (chance <= 95) return "legendary";
            else return "mystic";
        }
        else if (playerStrength >= getStrengthNecessary("legendary")) {
            if (chance <= 60) return "common";
            else if (chance <= 85) return "rare";
            else return "legendary";
        }
        else if (playerStrength >= getStrengthNecessary("rare")) {
            if (chance <= 70) return "common";
            else return "rare";
        }
        else {
            return "common";
        }
    }

    public static List<String> getFishes(String rarity) {
        YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
        List<String> fishes = new ArrayList<>();

        for (String fish : config.getConfigurationSection("fishes").getKeys(false)) {
            String fishRarity = config.getString("fishes." + fish + ".rarity");
            assert fishRarity != null;
            if (fishRarity.equalsIgnoreCase(rarity)) {
                fishes.add(fish);
            }
        }

        return fishes;
    }

    public static void giveFish(Player player, String fish) {
        PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();
            switch (fish) {
                case "bacalhau" -> cacheMethod.setBacalhau(player, cacheMethod.getBacalhau(player) + 1);
                case "salmao" ->  cacheMethod.setSalmao(player, cacheMethod.getSalmao(player) + 1);
                case "caranguejo" -> cacheMethod.setCaranguejo(player, cacheMethod.getCaranguejo(player) + 1);
                case "lagosta" -> cacheMethod.setLagosta(player, cacheMethod.getLagosta(player) + 1);
                case "lula" -> cacheMethod.setLula(player, cacheMethod.getLula(player) + 1);
                case "lula_brilhante" -> cacheMethod.setLulaBrilhante(player, cacheMethod.getLulaBrilhante(player) + 1);
                case "tubarao" -> cacheMethod.setTubarao(player, cacheMethod.getTubarao(player) + 1);
                case "baleia" -> cacheMethod.setBaleia(player, cacheMethod.getBaleia(player) + 1);
            }
    }
}
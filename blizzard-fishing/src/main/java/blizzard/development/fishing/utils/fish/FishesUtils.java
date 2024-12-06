package blizzard.development.fishing.utils.fish;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishesUtils {

    private static FishesUtils instance;
    private final Random random = new Random();
    private final YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();

    public int getStrengthNecessary(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("strengthNeeded.common");
            case "rare" -> config.getInt("strengthNeeded.rare");
            case "legendary" -> config.getInt("strengthNeeded.legendary");
            case "mystic" -> config.getInt("strengthNeeded.mystic");
            default -> 0;
        };
    }

    public int getFishXp(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("xpByRarity.common");
            case "rare" -> config.getInt("xpByRarity.rare");
            case "legendary" -> config.getInt("xpByRarity.legendary");
            case "mystic" -> config.getInt("xpByRarity.mystic");
            case "frozen" -> config.getInt("xpByRarity.frozen");
            default -> 0;
        };
    }

    public int getFishValue(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> config.getInt("valueByRarity.common");
            case "rare" -> config.getInt("valueByRarity.rare");
            case "legendary" -> config.getInt("valueByRarity.legendary");
            case "mystic" -> config.getInt("valueByRarity.mystic");
            case "frozen" -> config.getInt("valueByRarity.frozen");
            default -> 0;
        };
    }

    public String getRarity(int playerStrength) {
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

    public List<String> getFishes(String rarity) {
        List<String> fishes = new ArrayList<>();

        if (CoreAPI.getInstance().isIsBlizzard()) {
            fishes.add("congelado");
            return fishes;
        }

        for (String fish : config.getConfigurationSection("fishes").getKeys(false)) {
            String fishRarity = config.getString("fishes." + fish + ".rarity");
            assert fishRarity != null;
            if (fishRarity.equalsIgnoreCase(rarity)) {
                fishes.add(fish);
            }
        }

        return fishes;
    }

    public void giveFish(Player player, String fish, PlayersCacheMethod cacheMethod) {
            switch (fish) {
                case "bacalhau" -> cacheMethod.setBacalhau(player, cacheMethod.getBacalhau(player) + 1);
                case "salmao" ->  cacheMethod.setSalmao(player, cacheMethod.getSalmao(player) + 1);
                case "caranguejo" -> cacheMethod.setCaranguejo(player, cacheMethod.getCaranguejo(player) + 1);
                case "lagosta" -> cacheMethod.setLagosta(player, cacheMethod.getLagosta(player) + 1);
                case "lula" -> cacheMethod.setLula(player, cacheMethod.getLula(player) + 1);
                case "lula_brilhante" -> cacheMethod.setLulaBrilhante(player, cacheMethod.getLulaBrilhante(player) + 1);
                case "tubarao" -> cacheMethod.setTubarao(player, cacheMethod.getTubarao(player) + 1);
                case "baleia" -> cacheMethod.setBaleia(player, cacheMethod.getBaleia(player) + 1);
                case "congelado" -> cacheMethod.setFrozenFish(player, cacheMethod.getFrozenFish(player) + 1);
            }
    }

    public String getRandomFish() {
        String[] fishes = {"bacalhau", "salmao", "caranguejo", "lagosta", "lula", "lula_brilhante", "tubarao", "baleia"};

        int index = random.nextInt(fishes.length);
        return fishes[index];
    }

    public void giveXp(Player player, String rarity, RodsCacheMethod rodsCacheMethod) {
        double xp = FishesUtils.getInstance().getFishXp(rarity);

        rodsCacheMethod.setXp(player, xp);

        xp = xp * rodsCacheMethod.getBestMaterial(player).getBonus();

        player.sendMessage("§aVocê ganhou " + xp + " de xp");
    }

    public boolean giveChanceFrozenFish(Player player, PlayersCacheMethod cacheMethod) {;
        int chance = random.nextInt(101);

        if (chance >= 50) {
            player.sendMessage("tinha chance baixa e ganhou peixe congelado");
            cacheMethod.setFrozenFish(player,cacheMethod.getFrozenFish(player) + 1);
            return true;
        }

        return false;
    }

    public static FishesUtils getInstance() {
        if (instance == null) {
            instance = new FishesUtils();
        }
        return instance;
    }
}
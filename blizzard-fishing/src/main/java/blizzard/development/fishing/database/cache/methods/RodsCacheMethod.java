package blizzard.development.fishing.database.cache.methods;

import blizzard.development.fishing.database.cache.RodsCacheManager;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RodsCacheMethod {
    private static RodsCacheMethod instance;

    private final RodsCacheManager cache = RodsCacheManager.getInstance();

    public int getStrength(Player player) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getStrength() : 0;
    }

    public void setStrength(Player player, int strength) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setStrength(strength);
            cache.cachePlayerData(player, data);
        }
    }

    public double getXp(Player player) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getXp() : 0;
    }

    public void setXp(Player player, double xp) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setXp(data.getXp() + xp);
            cache.cachePlayerData(player, data);
        }
    }

    public int getExperienced(Player player) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getExperienced() : 0;
    }

    public void setExperienced(Player player, int experienced) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setExperienced(experienced);
            cache.cachePlayerData(player, data);
        }
    }

    public int getLucky(Player player) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getLucky() : 0;
    }

    public void setLucky(Player player, int lucky) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setLucky(lucky);
            cache.cachePlayerData(player, data);
        }
    }

    public List<RodMaterials> getMaterials(Player player) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getRodMaterials() : Arrays.asList();
    }

    public void addMaterial(Player player, RodMaterials material) {
        RodsData data = RodsCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            List<RodMaterials> materials = new ArrayList<>(data.getRodMaterials()); // Cria uma cópia mutável
            if (!materials.contains(material)) {
                materials.add(material); // Modifica a lista mutável
                data.setRodMaterials(materials); // Define a lista atualizada em data
                cache.cachePlayerData(player, data); // Atualiza os dados no cache
            }
        }
    }

    public RodMaterials getBestMaterial(Player player) {
        List<RodMaterials> materials = RodsCacheMethod.getInstance().getMaterials(player);
        List<RodMaterials> materialPriority = Arrays.asList(RodMaterials.CARBON, RodMaterials.IRON, RodMaterials.WOOD, RodMaterials.BAMBOO);

        for (RodMaterials material : materialPriority) {
            if (materials.contains(material)) {
                return material;
            }
        }

        return RodMaterials.BAMBOO;
    }


    public static RodsCacheMethod getInstance() {
        if (instance == null) {
            instance = new RodsCacheMethod();
        }
        return instance;
    }


}

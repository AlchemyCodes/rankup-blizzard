package blizzard.development.spawners.utils;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.enums.States;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SpawnersUtils {
    private static SpawnersUtils instance;

    public EntityType getEntityTypeFromSpawner(Spawners spawnerType) {
        return switch (spawnerType.getType().toLowerCase()) {
            case "porco" -> EntityType.PIG;
            case "vaca" -> EntityType.COW;
            case "coguvaca" -> EntityType.MUSHROOM_COW;
            case "ovelha" -> EntityType.SHEEP;
            case "zumbi" -> EntityType.ZOMBIE;
            default -> null;
        };
    }

    public Spawners getSpawnerFromName(String name) {
        return switch (name.toLowerCase()) {
            case "porco" -> Spawners.PIG;
            case "vaca" -> Spawners.COW;
            case "coguvaca" -> Spawners.MOOSHROOM;
            case "ovelha" -> Spawners.SHEEP;
            case "zumbi" -> Spawners.ZOMBIE;
            default -> null;
        };
    }

    public String getMobNameByEntity(EntityType entityType) {
        if (entityType.equals(EntityType.PIG)) {
            return "Porco";
        } else if (entityType.equals(EntityType.COW)) {
            return "Vaca";
        } else if (entityType.equals(EntityType.MUSHROOM_COW)) {
            return "Coguvaca";
        } else if (entityType.equals(EntityType.SHEEP)) {
            return "Ovelha";
        } else if (entityType.equals(EntityType.ZOMBIE)) {
            return "Zumbi";
        }
        return null;
    }

    public String getMobNameBySpawner(Spawners spawner) {
        if (spawner.equals(Spawners.PIG)) {
            return "Porco";
        } else if (spawner.equals(Spawners.COW)) {
            return "Vaca";
        } else if (spawner.equals(Spawners.MOOSHROOM)) {
            return "Coguvaca";
        } else if (spawner.equals(Spawners.SHEEP)) {
            return "Ovelha";
        } else if (spawner.equals(Spawners.ZOMBIE)) {
            return "Zumbi";
        }
        return null;
    }

    public String getMobNameByData(SpawnersData data) {
        if (data.getType().equalsIgnoreCase("porco")) {
            return "Porco";
        } else if (data.getType().equalsIgnoreCase("vaca")) {
            return "Vaca";
        } else if (data.getType().equalsIgnoreCase("coguvaca")) {
            return "Coguvaca";
        } else if (data.getType().equalsIgnoreCase("ovelha")) {
            return "Ovelha";
        } else if (data.getType().equalsIgnoreCase("zumbi")) {
            return "Zumbi";
        }
        return null;
    }

    public String getEnchantmentName(Enchantments enchantment) {
        if (enchantment.getName().equalsIgnoreCase(Enchantments.SPEED.getName())) {
            return "Velocidade";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantments.LUCKY.getName())) {
            return "Sortudo";
        } else if (enchantment.getName().equalsIgnoreCase(Enchantments.EXPERIENCE.getName())) {
            return "Experiente";
        }
        return null;
    }

    public String getSpawnerState(States state) {
        if (state.getState().equalsIgnoreCase(States.PUBLIC.getState())) {
            return "§aPúblico";
        } else if (state.getState().equalsIgnoreCase(States.PRIVATE.getState())) {
            return "§cPrivado";
        }
        return null;
    }

    public int getSpawnerDroppedXP(SpawnersData data) {
        if (data.getType().equalsIgnoreCase("porco")) {
            return 1;
        } else if (data.getType().equalsIgnoreCase("vaca")) {
            return 2;
        } else if (data.getType().equalsIgnoreCase("coguvaca")) {
            return 3;
        } else if (data.getType().equalsIgnoreCase("ovelha")) {
            return 4;
        } else if (data.getType().equalsIgnoreCase("zumbi")) {
            return 5;
        }
        return 0;
    }

    public boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE
                || material == Material.STONE_PICKAXE
                || material == Material.IRON_PICKAXE
                || material == Material.GOLDEN_PICKAXE
                || material == Material.DIAMOND_PICKAXE
                || material == Material.NETHERITE_PICKAXE;
    }

    public static SpawnersUtils getInstance() {
        if (instance == null) instance = new SpawnersUtils();
        return instance;
    }
}

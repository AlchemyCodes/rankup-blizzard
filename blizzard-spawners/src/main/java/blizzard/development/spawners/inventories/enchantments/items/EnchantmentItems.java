package blizzard.development.spawners.inventories.enchantments.items;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class EnchantmentItems {
    private static EnchantmentItems instance;

    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
    private final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();
    private final NumberFormat format = NumberFormat.getInstance();

    public ItemStack speed(Player player, String id, Enchantments enchantment) {
        ItemStack item = new ItemStack(Material.HOPPER);
        ItemMeta meta = item.getItemMeta();

        String display;
        if (getters.getSpawnerSpeedLevel(id) <= handler.getMaxLevel(enchantment.getName())) {
            display = "§cVelocidade §l" + format.formatNumber(getters.getSpawnerSpeedLevel(id));
        } else {
            display = "§bVelocidade §l" + format.formatNumber(getters.getSpawnerSpeedLevel(id));
        }

        List<String> lore;
        if (getters.getSpawnerSpeedLevel(id) <= handler.getMaxLevel(enchantment.getName())) {
            lore = Arrays.asList(
                    "§7Diminua a velocidade de",
                    "§7geração do seu spawner.",
                    "",
                    "§f Nível: §c" + format.formatNumber(getters.getSpawnerSpeedLevel(id)) + "/" + format.formatNumber(handler.getInitialLevel(enchantment.getName())),
                    "§f Tempo: §c" + format.formatNumber(handler.getPerLevel(enchantment.getName()) * getters.getSpawnerSpeedLevel(id)) + "§ls",
                    "",
                    "§cJá está no máximo."
            );
        } else {
            lore = Arrays.asList(
                    "§7Diminua a velocidade de",
                    "§7geração do seu spawner.",
                    "",
                    "§f Nível: §b" + format.formatNumber(getters.getSpawnerSpeedLevel(id)) + "/" + format.formatNumber(handler.getInitialLevel(enchantment.getName())),
                    "§f Tempo: §b" + format.formatNumber((handler.getPerLevel(enchantment.getName()) * getters.getSpawnerSpeedLevel(id))) + "§ls",
                    "",
                    "§bClique para melhorar."
            );
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack lucky(Player player, String id, Enchantments enchantment) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();

        String display;
        if (getters.getSpawnerLuckyLevel(id) >= handler.getMaxLevel(enchantment.getName())) {
            display = "§cSortudo §l" + format.formatNumber(getters.getSpawnerLuckyLevel(id));
        } else {
            display = "§6Sortudo §l" + format.formatNumber(getters.getSpawnerLuckyLevel(id));
        }

        List<String> lore;
        if (getters.getSpawnerLuckyLevel(id) >= handler.getMaxLevel(enchantment.getName())) {
            lore = Arrays.asList(
                    "§7Aumente suas chances",
                    "§7de ganhar recompensas.",
                    "§7ao matar mobs do spawner",
                    "",
                    "§f Nível: §c" + format.formatNumber(getters.getSpawnerLuckyLevel(id)) + "/" + format.formatNumber(handler.getMaxLevel(enchantment.getName())),
                    "§f Chance: §c" + format.formatNumber((handler.getPerLevel(enchantment.getName()) * getters.getSpawnerLuckyLevel(id))) + "§l%",
                    "",
                    "§cJá está no máximo."
            );
        } else {
            lore = Arrays.asList(
                    "§7Aumente suas chances",
                    "§7de ganhar recompensas.",
                    "§7ao matar mobs do spawner",
                    "",
                    "§f Nível: §6" + format.formatNumber(getters.getSpawnerLuckyLevel(id)) + "/" + format.formatNumber(handler.getMaxLevel(enchantment.getName())),
                    "§f Chance: §6" + format.formatNumber((handler.getPerLevel(enchantment.getName()) * getters.getSpawnerLuckyLevel(id))) + "§l%",
                    "",
                    "§6Clique para melhorar."
            );
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack experience(Player player, String id, Enchantments enchantment) {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();

        String display;
        if (getters.getSpawnerExperienceLevel(id) >= handler.getMaxLevel(enchantment.getName())) {
            display = "§cExperiente §l" + format.formatNumber(getters.getSpawnerExperienceLevel(id));
        } else {
            display = "§aExperiente §l" + format.formatNumber(getters.getSpawnerExperienceLevel(id));
        }

        List<String> lore;
        if (getters.getSpawnerExperienceLevel(id) >= handler.getMaxLevel(enchantment.getName())) {
            lore = Arrays.asList(
                    "§7Aumente o ganho de",
                    "§7experiência ao matar",
                    "§7os mobs do spawner.",
                    "",
                    "§f Nível: §c" + format.formatNumber(getters.getSpawnerExperienceLevel(id)) + "/" + format.formatNumber(handler.getMaxLevel(enchantment.getName())),
                    "§f Ganho: §c" + format.formatNumber((
                            handler.getPerLevel(enchantment.getName())
                                    * getters.getSpawnerExperienceLevel(id)
                                    * SpawnersUtils.getInstance().getSpawnerDroppedXP(
                                    SpawnersCacheManager.getInstance().getSpawnerData(id)))) + "§lXP",
                    "",
                    "§cJá está no máximo."
            );
        } else {
            lore = Arrays.asList(
                    "§7Aumente o ganho de",
                    "§7experiência ao matar",
                    "§7os mobs do spawner.",
                    "",
                    "§f Nível: §a" + format.formatNumber(getters.getSpawnerExperienceLevel(id)) + "/" + format.formatNumber(handler.getMaxLevel(enchantment.getName())),
                    "§f Ganho: §a" + format.formatNumber((
                            handler.getPerLevel(enchantment.getName())
                                    * getters.getSpawnerExperienceLevel(id)
                                    * SpawnersUtils.getInstance().getSpawnerDroppedXP(
                                    SpawnersCacheManager.getInstance().getSpawnerData(id)))) + "§lXP",
                    "",
                    "§aClique para melhorar."
            );
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(Arrays.asList(
                "§7Clique aqui para voltar",
                "§7ao menu anterior."
        ));

        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static EnchantmentItems getInstance() {
        if (instance == null) instance = new EnchantmentItems();
        return instance;
    }
}

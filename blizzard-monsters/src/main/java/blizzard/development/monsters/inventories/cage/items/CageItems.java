package blizzard.development.monsters.inventories.cage.items;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.eggs.MonstersEggManager;
import blizzard.development.monsters.utils.NumberFormatter;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CageItems {
    private static CageItems instance;

    public ItemStack monster(Player player, String monster) {
        MonstersEggManager eggManager = MonstersEggManager.getInstance();

        Material material;
        if (eggManager.getMaterial(monster) != null) {
            material = Material.getMaterial(eggManager.getMaterial(monster));
        } else {
            material = Material.EGG;
        }

        ItemStack item = new ItemBuilder(material).build(false);
        ItemMeta meta = item.getItemMeta();

        String display;
        if (eggManager.getDisplayName(monster) != null) {
            display = eggManager.getDisplayName(monster);
        } else {
            display = "§bMonstro corrompido";
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(Arrays.asList(
                "§7Capture este monstro.",
                "",
                "§8 ▶ §fQuantia: §7" + NumberFormatter.getInstance().formatNumber(getMonstersAmount(player, monster)),
                "",
                "§f Opções disponíveis:",
                "§8  ■ §fBotão Esquerdo: §7Capture um.",
                "§8  ■ §fBotão Direito: §7Capture todos.",
                ""
        ));

        int monstersAmount;
        if (getMonstersAmount(player, monster) <= 0) {
            monstersAmount = 1;
        } else if (getMonstersAmount(player, monster) >= 64) {
            monstersAmount = 64;
        } else {
            monstersAmount = getMonstersAmount(player, monster);
        }

        item.setAmount(monstersAmount);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack catchAll() {
        ItemStack item = new ItemStack(Material.HOPPER_MINECART);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aCapturar todos");
        meta.setLore(Arrays.asList(
                "§7Capture todos os monstros",
                "§7que estão presos na gaiola.",
                "",
                "§aClique para capturar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack nothing() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§7Nenhum monstro"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack next() {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aAvançar"));
        meta.setLore(List.of("§7Clique aqui para avançar."));
        item.setItemMeta(meta);
        return item;
    }

    private int getMonstersAmount(Player player, String monster) {
        int playerMonsters = 0;
        for (MonstersData data : MonstersCacheManager.getInstance().monstersCache.values()) {
            if (data.getOwner().equals(player.getName())) {
                if (data.getType().equals(monster)) {
                    playerMonsters++;
                }
            }
        }
        return playerMonsters;
    }

    public static CageItems getInstance() {
        if (instance == null) instance = new CageItems();
        return instance;
    }
}

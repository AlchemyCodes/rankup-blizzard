package blizzard.development.monsters.monsters.managers.tools;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.database.cache.managers.ToolsCacheManager;
import blizzard.development.monsters.database.storage.ToolsData;
import blizzard.development.monsters.managers.DataBatchManager;
import blizzard.development.monsters.monsters.enums.Tools;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class MonstersToolManager {
    private static MonstersToolManager instance;

    public void giveSword(Player player, Integer damage, Integer experienced) {
        String swordId = UUID.randomUUID().toString().substring(0, 10);

        String sword = "blizzard.monsters.sword";

        ItemStack item = new ItemBuilder(Material.WOODEN_SWORD)
                .setDisplayName("§6Aniquiladora §l✂")
                .setLore(Arrays.asList(
                        "§7Use esta espada para",
                        "§7aniquilar os monstros.",
                        "",
                        " §fDano: §c❤" + damage,
                        " §fExperiente: §a★" + experienced,
                        ""
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .addPersistentData(PluginImpl.getInstance().plugin, sword, swordId)
                .build(true);

        player.getInventory().addItem(item);

        String type = Tools.SWORD.getType();

        createSwordData(player, swordId, type, damage, experienced);
    }

    private void createSwordData(Player player, String id, String type, Integer damage, Integer experienced) {
        String owner = player.getName();

        ToolsData toolsData = new ToolsData(
                id,
                type,
                damage,
                experienced,
                owner
        );

        try {
            DataBatchManager.addToPendingQueue(toolsData);
            ToolsCacheManager.getInstance().cacheToolData(id, toolsData);
        } catch (Exception ex) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao salvar essa ferramenta no banco de dados!"));
            ex.printStackTrace();
        }
    }

    public void giveRadar(Player player) {
        String radar = "blizzard.monsters.compass";

        ItemStack item = new ItemBuilder(Material.COMPASS)
                .setDisplayName("§cRadar de Monstros \uD83D\uDCE1")
                .setLore(Arrays.asList(
                        "§7Use este radar para",
                        "§7localizar os monstros.",
                        "",
                        "§cClique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addPersistentData(PluginImpl.getInstance().plugin, radar, player.getName())
                .build(false);

        player.getInventory().addItem(item);
    }

    public void removeRadar(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.monsters.compass")) {
                player.getInventory().remove(item);
            }
        }
    }

    public static MonstersToolManager getInstance() {
        if (instance == null) instance = new MonstersToolManager();
        return instance;
    }
}

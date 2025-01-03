package blizzard.development.plantations.listeners.visuals;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.plantations.enums.ToolsEnum;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class VisualApplyListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack cursor = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();

        if (cursor.getType() == Material.AIR) {
            return;
        }

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin("blizzard-visuals");

        if (!(hasPersistentData(plugin, cursor, "diamond-visual") ||
            hasPersistentData(plugin, cursor, "gold-visual") ||
            hasPersistentData(plugin, cursor, "iron-visual") ||
            hasPersistentData(plugin, cursor, "stone-visual"))) {
            return;
        }


        if (!hasPersistentData(Main.getInstance(), currentItem, "ferramenta")) {
            player.sendMessage(" nao pode");
            event.setCancelled(true);
            return;
        }

        if (hasPersistentData(plugin, cursor, "diamond-visual")) {
            String id = getPersistentData(Main.getInstance(), currentItem, "ferramenta-id");

            ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
            toolCacheMethod.setSkin(id, ToolsEnum.DIAMOND);

            event.setCurrentItem(
                ToolBuildItem
                    .tool(
                        id,
                        toolCacheMethod.getBlocks(id),
                        toolCacheMethod.getBotany(id),
                        toolCacheMethod.getAgility(id),
                        toolCacheMethod.getExplosion(id),
                        toolCacheMethod.getThunderstorm(id),
                        toolCacheMethod.getXray(id),
                        toolCacheMethod.getBlizzard(id),
                        1
                    )
            );

            player.sendMessage("");
            player.sendMessage(" §b§lYeah! §bVocê aplicou a skin de §lDiamante!");
            player.sendMessage(" §7Essa skin possui um bônus de §b3.3§lx");
            player.sendMessage("");

            event.getView().setCursor(null);
            event.setCancelled(true);
        }

        if (hasPersistentData(plugin, cursor, "gold-visual")) {
            String id = getPersistentData(Main.getInstance(), currentItem, "ferramenta-id");

            ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
            toolCacheMethod.setSkin(id, ToolsEnum.GOLD);

            event.setCurrentItem(
                ToolBuildItem
                    .tool(
                        id,
                        toolCacheMethod.getBlocks(id),
                        toolCacheMethod.getBotany(id),
                        toolCacheMethod.getAgility(id),
                        toolCacheMethod.getExplosion(id),
                        toolCacheMethod.getThunderstorm(id),
                        toolCacheMethod.getXray(id),
                        toolCacheMethod.getBlizzard(id),
                        1
                    )
            );

            player.sendMessage("");
            player.sendMessage(" §6§lYeah! §6Você aplicou a skin de §lOuro!");
            player.sendMessage(" §7Essa skin possui um bônus de §62.6§lx");
            player.sendMessage("");

            event.getView().setCursor(null);
            event.setCancelled(true);
        }

        if (hasPersistentData(plugin, cursor, "iron-visual")) {
            String id = getPersistentData(Main.getInstance(), currentItem, "ferramenta-id");

            ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
            toolCacheMethod.setSkin(id, ToolsEnum.IRON);

            event.setCurrentItem(
                ToolBuildItem
                    .tool(
                        id,
                        toolCacheMethod.getBlocks(id),
                        toolCacheMethod.getBotany(id),
                        toolCacheMethod.getAgility(id),
                        toolCacheMethod.getExplosion(id),
                        toolCacheMethod.getThunderstorm(id),
                        toolCacheMethod.getXray(id),
                        toolCacheMethod.getBlizzard(id),
                        1
                    )
            );

            player.sendMessage("");
            player.sendMessage(" §f§lYeah! §fVocê aplicou a skin de §lFerro!");
            player.sendMessage(" §7Essa skin possui um bônus de §f1.9§lx");
            player.sendMessage("");

            event.getView().setCursor(null);
            event.setCancelled(true);
        }

        if (hasPersistentData(plugin, cursor, "stone-visual")) {
            String id = getPersistentData(Main.getInstance(), currentItem, "ferramenta-id");

            ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
            toolCacheMethod.setSkin(id, ToolsEnum.STONE);

            event.setCurrentItem(
                ToolBuildItem
                    .tool(
                        id,
                        toolCacheMethod.getBlocks(id),
                        toolCacheMethod.getBotany(id),
                        toolCacheMethod.getAgility(id),
                        toolCacheMethod.getExplosion(id),
                        toolCacheMethod.getThunderstorm(id),
                        toolCacheMethod.getXray(id),
                        toolCacheMethod.getBlizzard(id),
                        1
                    )
            );

            player.sendMessage("");
            player.sendMessage(" §8§lYeah! §8Você aplicou a skin de §lPedra!");
            player.sendMessage(" §7Essa skin possui um bônus de §81.1§lx");
            player.sendMessage("");

            event.getView().setCursor(null);
            event.setCancelled(true);
        }
    }

}

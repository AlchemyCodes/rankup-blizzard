package blizzard.development.mine.listeners.visual;

import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.mine.enums.ToolEnum;
import blizzard.development.mine.mine.item.ToolBuildItem;
import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static blizzard.development.mine.builders.item.ItemBuilder.getPersistentData;
import static blizzard.development.mine.builders.item.ItemBuilder.hasPersistentData;

public class VisualApplyListener implements Listener {

    private final ToolCacheMethods toolCacheMethods = ToolCacheMethods.getInstance();

    private final String pickaxeKey = "blizzard.mine.tool";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack cursor = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();

        if (cursor == null || cursor.getType() == Material.AIR) {
            return;
        }

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }

        Plugin visualPlugin = Bukkit.getPluginManager().getPlugin("blizzard-visuals");
        Plugin toolPlugin = PluginImpl.getInstance().plugin;

        if (!(hasPersistentData(visualPlugin, cursor, "diamond-visual") ||
                hasPersistentData(visualPlugin, cursor, "gold-visual") ||
                hasPersistentData(visualPlugin, cursor, "iron-visual") ||
                hasPersistentData(visualPlugin, cursor, "stone-visual"))) {
            return;
        }

        if (hasPersistentData(toolPlugin, currentItem, pickaxeKey)) {
            String id = getPersistentData(toolPlugin, currentItem, pickaxeKey);

            ToolData toolsData = ToolCacheManager.getInstance().getToolData(id);
            if (toolsData == null) {
                return;
            }

            boolean applied = false;

            if (applySkin(event, player, visualPlugin, cursor, id, toolsData, ToolEnum.DIAMOND, "diamond-visual", "§b§lYeah! §bVocê aplicou a skin de §lDiamante!", "§b")) {
                applied = true;
            } else if (applySkin(event, player, visualPlugin, cursor, id, toolsData, ToolEnum.GOLD, "gold-visual", "§6§lYeah! §6Você aplicou a skin de §lOuro!", "§6")) {
                applied = true;
            } else if (applySkin(event, player, visualPlugin, cursor, id, toolsData, ToolEnum.IRON, "iron-visual", "§f§lYeah! §fVocê aplicou a skin de §lFerro!", "§f")) {
                applied = true;
            } else if (applySkin(event, player, visualPlugin, cursor, id, toolsData, ToolEnum.STONE, "stone-visual", "§8§lYeah! §8Você aplicou a skin de §lPedra!", "§8")) {
                applied = true;
            }

            if (applied) {
                manageStack(event);
            }
        }
    }

    private boolean applySkin(InventoryClickEvent event, Player player, Plugin visualPlugin, ItemStack cursor, String id, ToolData toolsData, ToolEnum tool, String visualKey, String message, String color) {
        if (!hasPersistentData(visualPlugin, cursor, visualKey)) {
            return false;
        }

        int visualPriority = ToolEnum.valueOf(getVisualSkin(visualPlugin, cursor).toUpperCase()).getPriority();
        int toolPriority = ToolEnum.valueOf(toolsData.getSkin().toUpperCase()).getPriority();

        if (visualPriority == toolPriority) {
            player.sendActionBar("§c§lEI! §cEssa ferramenta já possui essa skin.");
            return false;
        }

        if (visualPriority < toolPriority) {
            player.sendActionBar("§c§lEI! §cEssa ferramenta já possui uma skin melhor.");
            return false;
        }

        toolCacheMethods.setSkin(id, tool);
        event.setCurrentItem(
                ToolBuildItem
                        .tool(
                                player,
                                false,
                                toolsData.getId(),
                                ToolEnum.valueOf(toolsData.getSkin().toUpperCase()),
                                toolsData.getBlocks(),
                                toolsData.getMeteor()
                        )
        );

        player.sendMessage("");
        player.sendMessage(message);
        player.sendMessage(" §7Essa skin possui um bônus de " + color + tool.getBonus() + "§lx");
        player.sendMessage("");
        return true;
    }

    private String getVisualSkin(Plugin visualPlugin, ItemStack cursor) {
        String skin;

        if (hasPersistentData(visualPlugin, cursor, "diamond-visual")) {
            skin = "diamond";
        } else if (hasPersistentData(visualPlugin, cursor, "gold-visual")) {
            skin = "gold";
        } else if (hasPersistentData(visualPlugin, cursor, "iron-visual")) {
            skin = "iron";
        } else if (hasPersistentData(visualPlugin, cursor, "stone-visual")) {
            skin = "stone";
        } else {
            skin = "wooden";
        }

        return skin;
    }

    private void manageStack(InventoryClickEvent event) {
        ItemStack cursorItem = event.getCursor();
        if (cursorItem.getAmount() > 1) {
            cursorItem.setAmount(cursorItem.getAmount() - 1);
        } else {
            event.getView().setCursor(null);
        }
        event.setCancelled(true);
    }
}
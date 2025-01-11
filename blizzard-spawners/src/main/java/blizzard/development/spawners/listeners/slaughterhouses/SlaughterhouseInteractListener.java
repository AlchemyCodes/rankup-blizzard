package blizzard.development.spawners.listeners.slaughterhouses;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.database.cache.getters.SlaughterhouseCacheGetters;
import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.handlers.enums.spawners.States;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.inventories.slaughterhouses.main.MainInventory;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SlaughterhouseInteractListener implements Listener {

    private final SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();

    @EventHandler
    public void onSlaughterhouseInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block slaughterhouseBlock = event.getClickedBlock();

        if (slaughterhouseBlock == null) return;

        if (event.getAction().isRightClick()) {
            if (slaughterhouseBlock.getType().equals(Material.GLASS)) {

                String serializedLocation = LocationUtil.getSerializedLocation(slaughterhouseBlock.getLocation());

                SlaughterhouseData data = null;
                for (SlaughterhouseData slaughterhouse : cache.slaughterhouseCache.values()) {
                    if (slaughterhouse.getLocation().equals(serializedLocation)) {
                        data = slaughterhouse;
                        break;
                    }
                }

                if (!LocationUtil.interactVerify(player, slaughterhouseBlock) && data != null && slaughterhouseBlock.getLocation().equals(
                        LocationUtil.deserializeLocation(data.getLocation())
                )) {
                    event.setCancelled(true);
                    return;
                }

                if (data == null) return;

                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, player.getInventory().getItemInMainHand(), "blizzard.spawners-friendslimit"
                ) || ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, player.getInventory().getItemInMainHand(), "blizzard.spawners-autosell")) {
                    event.setCancelled(true);
                    return;
                }

                boolean released = SlaughterhouseHandler.getInstance().isSlaughterhouseReleased(Integer.parseInt(data.getTier()));

                if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                    player.sendActionBar(TextAPI.parse(
                            "§c§lEI! §cEste abatedouro não está liberado.")
                    );
                    event.setCancelled(true);
                    return;
                }

                if (
                        !player.getName().equals(data.getNickname())
                                && !player.hasPermission("blizzard.spawners.admin")
                                && !data.getFriends().contains(player.getName())
                ) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse abatedouro."));
                    player.getInventory().close();
                    return;
                }

                MainInventory.getInstance().open(player, data.getId());
            }
        }
    }
}

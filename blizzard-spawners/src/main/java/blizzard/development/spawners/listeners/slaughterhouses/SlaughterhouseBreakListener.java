package blizzard.development.spawners.listeners.slaughterhouses;

import blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder;
import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.dao.SlaughterhouseDAO;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.managers.spawners.SpawnerAccessManager;
import blizzard.development.spawners.utils.*;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlaughterhouseBreakListener implements Listener {

    private final SlaughterhouseDAO slaughterhouseDAO = new SlaughterhouseDAO();
    private final SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();
    private final SlaughterhouseHandler handler = SlaughterhouseHandler.getInstance();
    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    @EventHandler
    public void onSlaughterhouseBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block slaughterhouseBlock = event.getBlock();
        String cooldownName = "blizzard.spawners.slaughterhouses.break-cooldown";

        if (slaughterhouseBlock.getType().equals(Material.GLASS)) {

            String serializedLocation = LocationUtil.getSerializedLocation(slaughterhouseBlock.getLocation());

            SlaughterhouseData data = null;
            for (SlaughterhouseData slaughterhouse : cache.slaughterhouseCache.values()) {
                if (slaughterhouse.getLocation().equals(serializedLocation)) {
                    data = slaughterhouse;
                    break;
                }
            }

            Material itemInHand = player.getInventory().getItemInMainHand().getType();
            if (player.getGameMode().equals(GameMode.CREATIVE) && itemInHand != Material.AIR && !SpawnersUtils.getInstance().isPickaxe(itemInHand)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê só pode quebrar abatedouros com a mão ou uma picareta."));
                event.setCancelled(true);
                return;
            }

            if (data == null) return;

            if (!player.getName().equals(data.getNickname()) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse abatedouro."));
                event.setCancelled(true);
                return;
            }

            boolean released = handler.isSlaughterhouseReleased(Integer.parseInt(data.getTier()));

            if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI! §cEste abatedouro não está liberado.")
                );
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de quebrar outro abatedouro."));
                event.setCancelled(true);
                return;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                event.setCancelled(true);
                return;
            }

            removeSlaughterhouse(player, slaughterhouseBlock, data.getId(), data.getTier());

            cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
        }
    }

    private void removeSlaughterhouse(Player player, Block block, String id, String level) {
        try {
            SlaughterhouseData slaughterhouseData = cache.getSlaughterhouseData(id);
            if (slaughterhouseData != null) {
                Location location = LocationUtil.deserializeLocation(slaughterhouseData.getLocation());
                DisplayBuilder.removeSlaughterhouseDisplay(location);
            }

            block.getWorld().getNearbyEntities(block.getLocation().clone().add(0.5, -1.0, 0.5), 1.0, 1.0, 1.0).stream()
                    .filter(entity -> entity instanceof ArmorStand)
                    .filter(entity -> entity.getLocation().getBlock().equals(block.getLocation().clone().add(0.5, -1.0, 0.5).getBlock()))
                    .forEach(Entity::remove);

            handler.giveSlaughterhouse(player, Integer.valueOf(slaughterhouseData.getTier()), 1);

            final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

            List<String> inventoryUsers = accessManager.getInventoryUsers(id);
            if (inventoryUsers != null) {
                for (String userId : inventoryUsers) {
                    Player openUser = Bukkit.getPlayer(userId);
                    if (openUser != null) {
                        Bukkit.getScheduler().runTask(PluginImpl.getInstance().plugin, () -> openUser.getOpenInventory().close());
                    }
                    accessManager.removeInventoryUser(id, userId);
                }
            }

            slaughterhouseDAO.deleteSlaughterhouseData(id);
            cache.removeSlaughterhouseData(id);

            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê removeu §fx" + 1 + " §aabatedouro(s) de nível " + level + "§a!"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
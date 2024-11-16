package blizzard.development.spawners.listeners.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.handlers.StaticMobs;
import blizzard.development.spawners.handlers.StaticSpawners;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.methods.SpawnersMethods;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnerPlaceListener implements Listener {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    @EventHandler
    private void onSpawnerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlockPlaced();
        String cooldownName = "blizzard.spawners.place-cooldown";

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {
            if (!terrainVerify(player, spawnerBlock)) {
                event.setCancelled(true);
                return;
            }

            if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa estar no modo sobrevivência para quebrar."));
                event.setCancelled(true);
                return;
            }

            if (hasNearbySpawners(spawnerBlock.getLocation(), 5)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cJá existe um spawner no raio de 5 blocos."));
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de colocar outro spawner."));
                event.setCancelled(true);
                return;
            }

            ItemStack spawnerItem = player.getInventory().getItemInMainHand();
            final String id = UUID.randomUUID().toString().substring(0, 8);

            final Spawners cowSpawner = Spawners.COW;
            final String cowKey = "blizzard.spawners-" + cowSpawner.getType();

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, cowKey)) {
                String value = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, cowKey);
                if (!setupSpawner(player, id, spawnerBlock.getLocation(), cowSpawner, Double.valueOf(value))) {
                    event.setCancelled(true);
                    return;
                }
            } else {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                event.setCancelled(true);
            }

            cooldown.createCountdown(player, cooldownName, 500, TimeUnit.MILLISECONDS);
        }
    }

    private Boolean setupSpawner(Player player, String id, Location spawnerLocation, Spawners spawner, Double amount) {
        StaticSpawners.setupCreatureSpawner(spawnerLocation, spawner);

        Location mobLocation = spawnerLocation.clone();
        Location playerLocation = player.getLocation();
        mobLocation.add(spawnerLocation.toVector().subtract(playerLocation.toVector()).normalize().multiply(-1).setY(0));
        mobLocation.add(0.5, 0, 0.5);

        if (!SpawnersMethods.createSpawner(player, id, LocationUtil.getSerializedLocation(spawnerLocation), spawner, amount, "N/A")) {
            return false;
        }

        StaticMobs.spawnStaticMob(player, spawner, mobLocation);

        DisplayBuilder.createSpawnerDisplay(spawnerLocation, spawner.getType(), amount, player);
        EffectsBuilder.createSpawnerEffect(player, spawnerLocation, spawner.getType());

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê colocou §fx" + formattedAmount + " §aspawner(s) de " + spawner.getType() + "§a!"));
        return true;
    }

    private Boolean terrainVerify(Player player, Block block) {
        com.plotsquared.core.location.Location blockLocation = getPlotLocation(block);

        UUID playerUUID = player.getUniqueId();
        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(blockLocation);

        if (plotArea == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        Plot plot = plotArea.getPlot(blockLocation);

        if (plot == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        if (!(Objects.equals(plot.getOwner(), playerUUID) || plot.isAdded(playerUUID) || player.hasPermission("blizzard.spawners.admin"))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não tem permissão para colocar um spawner nesse local."));
            return false;
        }
        return true;
    }

    private com.plotsquared.core.location.Location getPlotLocation(Block block) {
        return com.plotsquared.core.location.Location.at(
                block.getLocation().getWorld().getName(),
                (int) block.getLocation().getX(),
                (int) block.getLocation().getY(),
                (int) block.getLocation().getZ()
        );
    }

    private boolean hasNearbySpawners(Location location, int radius) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    Location checkLoc = new Location(location.getWorld(), x + i, y + j, z + k);

                    if (i == 0 && j == 0 && k == 0) continue;

                    if (checkLoc.getBlock().getType() == Material.SPAWNER &&
                            checkLoc.distance(location) <= radius) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

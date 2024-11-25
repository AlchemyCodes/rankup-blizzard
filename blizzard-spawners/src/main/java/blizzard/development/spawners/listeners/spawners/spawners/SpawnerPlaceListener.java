package blizzard.development.spawners.listeners.spawners.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.methods.SpawnersMethods;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnerPlaceListener implements Listener {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();
    private final SpawnersHandler spawnersHandler = SpawnersHandler.getInstance();
    private final EnchantmentsHandler enchantmentsHandler = EnchantmentsHandler.getInstance();


    @EventHandler
    private void onSpawnerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlockPlaced();
        String cooldownName = "blizzard.spawners.place-cooldown";

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {
            if (!LocationUtil.terrainVerify(player, spawnerBlock)) {
                event.setCancelled(true);
                return;
            }

            if (LocationUtil.hasNearbySpawners(spawnerBlock.getLocation(), 5)) {
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

            boolean found = false;

            for (Spawners spawnerType : Spawners.values()) {
                final String key = "blizzard.spawners-" + spawnerType.getType();

                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key)) {
                    String value = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, key);
                    if (!setupSpawner(player, id, spawnerBlock.getLocation(), spawnerType, Double.valueOf(value))) {
                        event.setCancelled(true);
                        return;
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                event.setCancelled(true);
                return;
            }

            cooldown.createCountdown(player, cooldownName, 500, TimeUnit.MILLISECONDS);
        }
    }

    private Boolean setupSpawner(Player player, String id, Location spawnerLocation, Spawners spawner, Double amount) {
        spawnersHandler.createStaticSpawner(spawnerLocation, spawner);

        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(LocationUtil.getPlotLocation(spawnerLocation.getBlock()));
        if (plotArea == null) return false;
        Plot plot = plotArea.getPlot(LocationUtil.getPlotLocation(spawnerLocation.getBlock()));

        Location mobLocation = spawnerLocation.clone();

        mobLocation.setX(mobLocation.getBlockX() + 0.5);
        mobLocation.setZ(mobLocation.getBlockZ() + 0.5);

        Vector direction = spawnerLocation.getDirection().normalize();
        direction.multiply(-1);

        mobLocation.add(direction.multiply(1.0));

        mobLocation.setDirection(direction);

        if (!SpawnersMethods.createSpawner(
                player,
                spawner,
                id,
                LocationUtil.getSerializedLocation(spawnerLocation),
                LocationUtil.getSerializedLocation(mobLocation),
                String.valueOf(plot.getId()),
                amount,
                0.0,
                0.0,
                enchantmentsHandler.getInitialLevel("speed"),
                enchantmentsHandler.getInitialLevel("lucky"),
                enchantmentsHandler.getInitialLevel("experience")))
        {
            return false;
        }

        SpawnersData spawnerData = SpawnersCacheManager.getInstance().getSpawnerData(id);
        SpawnersMobsTaskManager.getInstance().startTask(spawnerData);

        DisplayBuilder.createSpawnerDisplay(spawnerLocation, spawner.getType(), amount, player);
        EffectsBuilder.createSpawnerEffect(player, spawnerLocation, spawner.getType());

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê colocou §fx" + formattedAmount + " §aspawner(s) de " + spawner.getType() + "§a!"));
        return true;
    }
}

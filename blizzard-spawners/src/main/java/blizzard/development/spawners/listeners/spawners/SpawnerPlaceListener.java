package blizzard.development.spawners.listeners.spawners;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.methods.SpawnersMethods;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.builders.ItemBuilder;
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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpawnerPlaceListener implements Listener {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block spawnerBlock = event.getBlockPlaced();
        String cooldownName = "blizzard.spawners.place-cooldown";

        if (spawnerBlock.getType().equals(Material.SPAWNER)) {
            if (!terrainVerify(player, spawnerBlock)) {
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de colocar outro spawner."));
                event.setCancelled(true);
                return;
            }

            ItemStack spawnerItem = player.getInventory().getItemInMainHand();

            final String id = UUID.randomUUID().toString();

            final Spawners pigSpawner = Spawners.PIG;
            final String pigKey = "blizzard.spawners-" + pigSpawner.getType();

            final Spawners cowSpawner = Spawners.COW;
            final String cowKey = "blizzard.spawners-" + cowSpawner.getType();

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, pigKey)) {
                String value = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, pigKey);
                setupSpawner(player, id, spawnerBlock.getLocation(), pigSpawner, Double.valueOf(value));

            } else if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, spawnerItem, cowKey)) {
                String value = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, spawnerItem, cowKey);
                setupSpawner(player, id, spawnerBlock.getLocation(), cowSpawner, Double.valueOf(value));

            } else {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cEste é um spawner sem dados."));
                event.setCancelled(true);
            }
            cooldown.createCountdown(player, cooldownName, 500, TimeUnit.MILLISECONDS);
        }
    }

    public void setupSpawner(Player player, String id, Location location, Spawners spawner, Double amount) {
        SpawnersMethods.createSpawner(player, id, LocationUtil.getSerializedLocation(location), spawner, amount);
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê colocou §fx" + formattedAmount + " §aspawner(s) de " + spawner.getType() + "§a!"));
    }

    public Boolean terrainVerify(Player player, Block block) {
        com.plotsquared.core.location.Location blockLocation = com.plotsquared.core.location.Location.at(
                block.getLocation().getWorld().getName(),
                (int) block.getLocation().getX(),
                (int) block.getLocation().getY(),
                (int) block.getLocation().getZ()
        );

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
}

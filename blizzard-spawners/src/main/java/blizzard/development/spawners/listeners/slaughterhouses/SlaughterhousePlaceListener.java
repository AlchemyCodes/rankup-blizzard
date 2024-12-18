package blizzard.development.spawners.listeners.slaughterhouses;

import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.managers.slaughterhouses.SlaughterhouseManager;
import blizzard.development.spawners.utils.*;
import blizzard.development.spawners.utils.items.SkullAPI;
import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SlaughterhousePlaceListener implements Listener {

    private final CooldownUtils cooldown = CooldownUtils.getInstance();
    private final SlaughterhouseHandler handler = SlaughterhouseHandler.getInstance();

    @EventHandler
    private void onSlaughterhousePlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block slaughterhouseBlock = event.getBlockPlaced();
        ItemStack slaughterhouseItem = player.getInventory().getItemInMainHand();
        String key = "blizzard.spawners.slaughterhouse";
        String cooldownName = "blizzard.spawners.slaughterhouses.place-cooldown";

        if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, slaughterhouseItem, key)) {
            if (!LocationUtil.terrainVerify(player, slaughterhouseBlock)) {
                event.setCancelled(true);
                return;
            }

            if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de colocar outro abatedouro."));
                event.setCancelled(true);
                return;
            }


            if (LocationUtil.hasNearbySlaughterhouses(slaughterhouseBlock.getLocation(), 5)) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cJá existe um abatedouro no raio de 5 blocos."));
                event.setCancelled(true);
                return;
            }

            final String id = UUID.randomUUID().toString().substring(0, 11);

            String tier = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, slaughterhouseItem, key);

            if (tier == null) {
                event.setCancelled(true);
                return;
            }

            boolean released = handler.isSlaughterhouseReleased(Integer.parseInt(tier));

            if (!released && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse(
                        "§c§lEI! §cEste abatedouro não está liberado.")
                );
                event.setCancelled(true);
                return;
            }

            if (setupSlaughterhouse(player, id, tier, slaughterhouseBlock.getLocation())) {
                createSlaughterhouseBlock(slaughterhouseBlock, Integer.parseInt(tier));
                event.setCancelled(true);
            }

            cooldown.createCountdown(player, cooldownName, 1000, TimeUnit.MILLISECONDS);
        }
    }

    private Boolean setupSlaughterhouse(Player player, String id, String tier, Location location) {
        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(LocationUtil.getPlotLocation(location.getBlock()));
        if (plotArea == null) return false;

        Plot plot = plotArea.getPlot(LocationUtil.getPlotLocation(location.getBlock()));

        if (!SlaughterhouseManager.createSlaughterhouse(
                player,
                id,
                tier,
                LocationUtil.getSerializedLocation(location),
                States.OFF.getState(),
                plot.getId().toString(),
                5
        )) {
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }

        DisplayBuilder.createSlaughterhouseDisplay(
                location,
                Integer.parseInt(tier),
                SlaughterhousesUtils.getInstance().getSlaughterhouseState(States.OFF),
                player.getName()
        );

        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê colocou §fx" + 1 + " §aabatedouro(s) nível " + tier + "§a!"));
        return true;
    }

    private void createSlaughterhouseBlock(Block block, Integer level) {
        Bukkit.getScheduler().runTaskLater(PluginImpl.getInstance().plugin, () -> block.setType(Material.GLASS), 1L);

        Location armorStandLocation = block.getLocation().clone().add(0.5, -0.5, 0.5);
        ArmorStand armorStand = (ArmorStand) block.getLocation().getWorld().spawnEntity(armorStandLocation, EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setArms(false);
        armorStand.setCanPickupItems(false);
        armorStand.setMarker(true);

        armorStand.getEquipment().setHelmet(
                SkullAPI.withBase64(new ItemStack(Material.PLAYER_HEAD), handler.getItem(level))
        );
    }
}

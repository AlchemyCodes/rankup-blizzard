package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.upgrades.ToolUpgradeManager;
import blizzard.development.plantations.managers.upgrades.plow.PlowManager;
import blizzard.development.plantations.plantations.events.PlantationBlockPlowEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class PlantationBlockListener implements Listener{

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        EquipmentSlot equipmentSlot = event.getHand();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (playerCacheMethod.isInPlantation(player)) {

            if (!hasPersistentData(Main.getInstance(), item, "ferramenta.arar")) {
                return;
            }

            if (action == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();

                if (equipmentSlot == EquipmentSlot.HAND) {
                    PlantationBlockPlowEvent plantationBlockPlowEvent = new PlantationBlockPlowEvent(player, block, block.getType(), equipmentSlot);
                    Bukkit.getPluginManager().callEvent(plantationBlockPlowEvent);
                }
            }
        }

    }

    @EventHandler
    public void whenPlow(PlantationBlockPlowEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (block.getType() == Material.COARSE_DIRT) {

            String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

            PlowManager.check(
                player,
                block,
                id
            );
            ToolUpgradeManager.checkPlowingTool(player);


            World world = block.getWorld();
            world.spawnParticle(Particle.COMPOSTER, block.getLocation().add(0.5, 1, 0.5), 3);
        }
    }

//    @EventHandler
//    public void onInteractTest(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        Block block = event.getClickedBlock();
//        Action action = event.getAction();
//        EquipmentSlot equipmentSlot = event.getHand();
//
//        if (block == null) return;
//
//        if (action == Action.RIGHT_CLICK_BLOCK && equipmentSlot == EquipmentSlot.HAND) {
//            Location blockLocation = block.getLocation();
//
//            player.sendMessage("§eMaterial antes: " + block.getType());
//
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    if (!player.isOnline()) {
//                        this.cancel();
//                        return;
//                    }
//
//                    sendPacket(player, blockLocation);
//                }
//            }.runTaskTimer(Main.getInstance(), 0L, 1L);
//
//            player.sendMessage("§eMaterial depois: " + blockLocation.getBlock().getType());
//        }
//    }

    @EventHandler
    public void onFarmlandDry(MoistureChangeEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTrample(EntityChangeBlockEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.FARMLAND) {
            event.setCancelled(true);

            Farmland farmland = (Farmland) block.getBlockData();
            farmland.setMoisture(farmland.getMaximumMoisture());
            block.setBlockData(farmland);
        }
    }

    private Block setMoisture(Block block) {
        Farmland farmland = (Farmland) block.getBlockData();
        farmland.setMoisture(farmland.getMaximumMoisture());
        block.setBlockData(farmland);
        return block;
    }

}

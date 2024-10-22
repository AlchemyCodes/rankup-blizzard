package blizzard.development.excavation.listeners.excavation;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.excavation.adapters.ExcavatorAdapter;
import blizzard.development.excavation.excavation.events.ExcavationBlockBreakEvent;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import blizzard.development.excavation.managers.RewardManager;
import blizzard.development.excavation.managers.upgrades.UpgradeExcavatorManager;
import blizzard.development.excavation.tasks.HologramTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.excavation.builder.ItemBuilder.hasPersistentData;

public class ExcavationListener implements Listener {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final UpgradeExcavatorManager upgradeExcavatorManager = new UpgradeExcavatorManager();
    private final HologramTask hologramTask = new HologramTask();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();


        ExcavationBlockBreakEvent excavationBlockBreakEvent = new ExcavationBlockBreakEvent(player, block);
        Bukkit.getPluginManager().callEvent(excavationBlockBreakEvent);

        if (playerCacheMethod.isInExcavation(player)) {
            if (excavationBlockBreakEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            if (!hasPersistentData(Main.getInstance(), item, "excavator.tool")) {
                player.sendActionBar("§c§lEI! §cUse uma ferramenta de escavação para isso.");
                excavationBlockBreakEvent.setCancelled(true);
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onExcavationBlockBreak(ExcavationBlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack item = player.getInventory().getItemInMainHand();

        ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();


        if (playerCacheMethod.isInExcavation(player)) {
            if (hasPersistentData(Main.getInstance(), item, "excavator.tool")) {
                double percentage = 1;

                player.getInventory().setItemInMainHand(excavatorBuildItem.buildExcavator(player));

                upgradeExcavatorManager.check(player);

                player.sendActionBar("§cVocê não encontrou nada :(");

                if (RewardManager.reward(percentage)) {
                    hologramTask.initializeHologramTask(player, block);

                    player.sendActionBar("§b§lYAY! §bVocê encontrou um Fóssil de Mamute.");
                }

                block.setType(Material.AIR);
                playerCacheMethod.setBlocks(player, playerCacheMethod.getBlocks(player) + 1);
            }
        }
    }



}

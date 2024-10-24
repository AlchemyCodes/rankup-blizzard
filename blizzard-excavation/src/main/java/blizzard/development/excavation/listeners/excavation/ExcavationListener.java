package blizzard.development.excavation.listeners.excavation;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.api.FossilAPI;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import blizzard.development.excavation.excavation.events.ExcavationBlockBreakEvent;
import blizzard.development.excavation.managers.RewardManager;
import blizzard.development.excavation.managers.upgrades.UpgradeExcavatorManager;
import blizzard.development.excavation.managers.upgrades.agility.AgilityManager;
import blizzard.development.excavation.managers.upgrades.extractor.ExcavatorBreakEffect;
import blizzard.development.excavation.tasks.BlockRegenTask;
import blizzard.development.excavation.tasks.HologramTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static blizzard.development.excavation.builder.ItemBuilder.hasPersistentData;

public class ExcavationListener implements Listener {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private final UpgradeExcavatorManager upgradeExcavatorManager = new UpgradeExcavatorManager();
    private final AgilityManager agilityManager = new AgilityManager();
    private final HologramTask hologramTask = new HologramTask();
    private final ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();
    public static Map<Player, Block> blocks = new HashMap<>();

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
            block.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onExcavationBlockBreak(ExcavationBlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();


        if (playerCacheMethod.isInExcavation(player)) {
            ItemStack item = player.getInventory().getItemInMainHand();


            if (hasPersistentData(Main.getInstance(), item, "excavator.tool")) {
                double percentage = 1;

                blocks.put(player, block);


                int efficiencyLevel = excavatorCacheMethod.effiencyEnchant(player.getName());
                int agilityLevel = excavatorCacheMethod.agilityEnchant(player.getName());
                int extractorLevel = excavatorCacheMethod.extractorEnchant(player.getName());

                player.getInventory().setItemInMainHand(excavatorBuildItem.buildExcavator(player, efficiencyLevel, agilityLevel, extractorLevel));


                upgradeExcavatorManager.check(player, block);
                agilityManager.check(player, excavatorCacheMethod);


                if (RewardManager.reward(percentage)) {
                    hologramTask.initializeHologramTask(player, block);
                    player.sendActionBar("§b§lYAY! §bVocê encontrou um Fóssil de Mamute.");

                    FossilAPI.setFossilBalance(player, FossilAPI.getFossilBalance(player) + 1);
                }

                ExcavationListener.blocks.forEach(((players, blocks) -> {
                    BlockRegenTask.create(blocks, Material.COARSE_DIRT, 5);
                }));

                playerCacheMethod.setBlocks(player, playerCacheMethod.getBlocks(player) + 1);
            }
        }
    }
}
package blizzard.development.monsters.listeners.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.monsters.handlers.eggs.MonstersEggHandler;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MonstersEggListener implements Listener {

    private final Plugin plugin = PluginImpl.getInstance().plugin;

    @EventHandler
    public void onEggInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            MonstersEggHandler eggHandler = MonstersEggHandler.getInstance();

            ItemStack item = player.getInventory().getItemInMainHand();

            boolean isMonsterEgg = ItemBuilder.hasPersistentData(plugin, item, "blizzard.monsters.monster");

            String eggType = ItemBuilder.getPersistentData(plugin, item, "blizzard.monsters.monster");
            String eggAmount = ItemBuilder.getPersistentData(plugin, item, "blizzard.monsters.monster-amount");

            if (isMonsterEgg) {
                player.sendMessage("é um monstro e do tipo " + eggType + " e a quantia de " + eggAmount);
                event.setCancelled(true);
            }
        }
    }
}

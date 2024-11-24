package blizzard.development.spawners.listeners.spawners.mobs;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobDeathListener implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player player = event.getEntity().getKiller();

        Entity mob = event.getEntity();

        if (mob.hasMetadata("blizzard_spawners-mob")) {
            event.getDrops().clear();

            String value = mob.getMetadata("blizzard_spawners-mob").get(0).asString();

            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            player.sendMessage("vose matou um " + value + "e ganhou bosta nenhuma");
        }
    }
}

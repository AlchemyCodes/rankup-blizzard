package blizzard.development.mine.listeners.npc;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.inventories.management.ManagementInventory;
import blizzard.development.mine.mine.events.npc.NPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCInteractListener implements Listener {

    @EventHandler
    public void onNPCInteract(NPCInteractEvent event) {
        Player player = event.getPlayer();

        if (!PlayerCacheMethods.getInstance().isInMine(player)) {
            return;
        }

        if (event.isCancelled()) return;

        Bukkit.getScheduler().runTask(Main.getInstance(),
                () -> new ManagementInventory()
                        .open(player)
        );
    }
}
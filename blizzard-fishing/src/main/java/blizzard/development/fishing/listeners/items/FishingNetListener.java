package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.tasks.items.FishingNetTask;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class FishingNetListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

        if (event.getItem() == null) return;

        if (!(FishingNetHandler.isNet(player))) return;

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            List<Block> los = player.getLineOfSight(null, 5);
            for (Block b : los) {
                    if (b.getType() == Material.WATER) {
                        if (FishingNetTask.isCatchingTrash(player)) {
                            FishingNetTask.setCatchingTrash(player, false);
                            player.sendTitle(config.getString("rede.pararPegarLixo.title"), config.getString("rede.pararPegarLixo.sub-title"));
                        } else {
                            FishingNetTask.setCatchingTrash(player, true);
                            player.sendTitle(config.getString("rede.comecarPegarLixo.title"), config.getString("rede.comecarPegarLixo.sub-title"));
                        }
                    }
                    event.setCancelled(true);
            }
        }

        event.setCancelled(true);
    }
}

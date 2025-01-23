package blizzard.development.mine.listeners.mine;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MineExtractorListener implements Listener {

    @EventHandler
    public void onExtractorInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        String extractor_metadata = "blizzard_mine_extractor-metadata";

        if (entity instanceof EnderCrystal) {
            if (entity.hasMetadata(extractor_metadata)) {
                player.sendMessage("tem nada aqui ainda");
            }
        }
    }
}

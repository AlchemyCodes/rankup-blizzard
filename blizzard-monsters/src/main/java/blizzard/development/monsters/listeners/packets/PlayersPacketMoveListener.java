package blizzard.development.monsters.listeners.packets;

import blizzard.development.monsters.monsters.handlers.packets.entity.EntityRotation;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayersPacketMoveListener implements Listener {
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        EntityRotation entityRotation = EntityRotation.getInstance();

//        if (player.getLocation().distance(location) <= 5) {
//            entityRotation.updateRotation(player, location, protocolManager);
//        }

    }
}

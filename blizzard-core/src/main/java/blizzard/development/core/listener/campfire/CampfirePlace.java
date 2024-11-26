package blizzard.development.core.listener.campfire;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.tasks.BlizzardTask;
import blizzard.development.core.utils.PluginImpl;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CampfirePlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.CAMPFIRE) {
            if (!CoreAPI.getInstance().isIsBlizzard()) {
                event.setCancelled(true);
                player.sendMessage("Você não precisa se aquecer agora!");
                return;
            }

            if (CampfireManager.hasCampfire(player)) {
                event.setCancelled(true);
                player.sendMessage("Você já tem uma fogueira!");
                return;
            }

            event.setCancelled(true);
            sendCampfire(player, block.getLocation().add(0, 1, 0));
            CampfireManager.placeCampfire(player, block.getLocation().add(0, 1, 0));
        }
    }

    private void sendCampfire(Player player, Location location) {
        Vector3i blockPosition = new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        StateType campfire = StateTypes.CAMPFIRE;

        WrapperPlayServerBlockChange blockChangePacket =
                new WrapperPlayServerBlockChange
                        (blockPosition, campfire.createBlockState().getGlobalId());

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, blockChangePacket);
    }
}

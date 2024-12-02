package blizzard.development.core.listener.campfire;

import blizzard.development.core.Main;
import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.tasks.BlizzardTask;
import blizzard.development.core.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CampfirePlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        BlockPosition campfirePosition = new BlockPosition(
                block.getLocation().add(0, 0, 0).getBlockX(),
                block.getLocation().add(0, 0, 0).getBlockY(),
                block.getLocation().add(0, 0, 0).getBlockZ()
        );

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
            CampfireManager.placeCampfire(player, campfirePosition);
            sendPacket(player, block.getLocation().add(0,0,0).getBlock());
        }
    }

    private void sendPacket(Player player, Block block) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

            packet.getBlockData().write(0, WrappedBlockData.createData(Material.CAMPFIRE));
            assert block != null;
            packet.getBlockPositionModifier().write(0, new BlockPosition(
                    block.getLocation().getBlockX(),
                    block.getLocation().getBlockY(),
                    block.getLocation().getBlockZ()
            ));

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Falhou sendpéquet");
        }
    }
}

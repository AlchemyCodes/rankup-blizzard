package blizzard.development.core.managers;

import blizzard.development.core.Main;
import blizzard.development.core.utils.PluginImpl;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SchematicManager {

    private final ProtocolManager protocolManager;

    public SchematicManager() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void pasteSchematicForPlayer(Player player, Location location) {
        File file = new File(Main.getInstance().getDataFolder(), PluginImpl.getInstance().Config.getString("schem.name"));

        if (!file.exists()) {
            player.sendMessage("Schematic file not found!");
            return;
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            Clipboard clipboard = ClipboardFormats.findByFile(file)
                    .getReader(inputStream)
                    .read();

            BlockVector3 origin = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            clipboard.getRegion().forEach(block -> {
                BlockVector3 relativePosition = block.subtract(clipboard.getRegion().getMinimumPoint());
                BlockVector3 worldPosition = origin.add(relativePosition);

                com.sk89q.worldedit.world.block.BlockState blockState = clipboard.getBlock(block);

                WrappedBlockData wrappedBlockData = WrappedBlockData.createData(
                        BukkitAdapter.adapt(blockState.getBlockType().getDefaultState().toBaseBlock())
                );

                sendBlockChange(player, worldPosition, wrappedBlockData);
            });

        } catch (Exception e) {
            throw new RuntimeException("Error while pasting schematic for player", e);
        }
    }

    private void sendBlockChange(Player player, BlockVector3 position, WrappedBlockData blockData) {
        try {
            PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.BLOCK_CHANGE);
            packet.getBlockPositionModifier().write(0, new BlockPosition(position.getBlockX(), position.getBlockY(), position.getBlockZ()));
            packet.getBlockData().write(0, blockData);
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

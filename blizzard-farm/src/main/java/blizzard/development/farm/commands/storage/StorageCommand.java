package blizzard.development.farm.commands.storage;

import blizzard.development.farm.farm.adapters.ToolAdapter;
import blizzard.development.farm.inventories.StorageInventory;
import blizzard.development.farm.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

@CommandAlias("armazem|storage")
public class StorageCommand extends BaseCommand {

    @Default
    public void onStorageCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        StorageInventory storageInventory = new StorageInventory();
        storageInventory.open(player);
    }

    @Subcommand("ferramenta")
    public void onToolCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        ToolAdapter.getInstance().giveTool(
            player
        );
    }

    @Subcommand("dev")
    public void onCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;
        Location baseLocation = player.getLocation().clone();

        int area = 15;

        new BukkitRunnable() {
            double i = 0;

            @Override
            public void run() {
                i += 0.1;

                int id = (int) Math.round(Math.random() * Integer.MAX_VALUE);

                double randomX = new Random().nextDouble(-area, area);
                double randomZ = new Random().nextDouble(-area, area);

                Location spawnLocation = baseLocation.clone().add(randomX, i, randomZ);

                spawnFallingBlock(
                    player,
                    spawnLocation,
                    id,
                    Material.COBBLESTONE
                );

                if (i >= 0.9) {
                    removeEntity(
                        player,
                        id
                    );
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 1L);
    }


    public static void spawnFallingBlock(Player player, Location location, Integer id, Material material) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
            .write(0, id);
        packetContainer.getUUIDs()
            .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
            .write(0, location.getX())
            .write(1, location.getY())
            .write(2, location.getZ());

        BlockData blockData = Bukkit.createBlockData(material);

        packetContainer.getIntegers().write(4, 1);

        packetContainer.getEntityTypeModifier().write(0, EntityType.FALLING_BLOCK);

        protocolManager.sendServerPacket(player, packetContainer);
    }

    public void removeEntity(Player player, Integer entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        final PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getIntLists().write(0, new IntArrayList(new int[]{entityId}));
        try {
            protocolManager.sendServerPacket(player, destroyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

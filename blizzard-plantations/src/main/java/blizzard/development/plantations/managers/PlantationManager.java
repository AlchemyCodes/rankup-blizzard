package blizzard.development.plantations.managers;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.utils.LocationUtils;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PlantationManager {

    private static final PlantationManager instance = new PlantationManager();

    public void transform(Player player, int radius) {
        Location centerLocation = LocationUtils.getCenterLocation();
        if (centerLocation == null) return;

        World world = centerLocation.getWorld();
        if (world == null) {
            return;
        }

        List<Block> farmlandBlocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    Location blockLocation = centerLocation.clone().add(x, y, z);
                    Block block = world.getBlockAt(blockLocation);
                    if (block.getType() == Material.FARMLAND) {
                        farmlandBlocks.add(block);
                    }
                }
            }
        }

        int batchSize = 600;
        int delayPerBatch = 10;

        new BukkitRunnable() {
            int batchIndex = 0;

            @Override
            public void run() {
                if (batchIndex * batchSize >= farmlandBlocks.size()) {
                    this.cancel();
                    return;
                }

                int start = batchIndex * batchSize;
                int end = Math.min(start + batchSize, farmlandBlocks.size());
                List<Block> batch = farmlandBlocks.subList(start, end);

                for (Block block : batch) {
                    BlockPosition blockPosition = new BlockPosition(
                        block.getX(),
                        block.getY(),
                        block.getZ()
                    );

                    BlockManager.placePlantation(player, blockPosition);
                    sendPacket(player, block);
                }

                batchIndex++;
            }
        }.runTaskTimer(Main.getInstance(), 0, delayPerBatch);
    }

    public void reset(Player player, int radius) {
        Location centerLocation = LocationUtils.getPlantationSpawnLocation();
        if (centerLocation == null) return;

        World world = centerLocation.getWorld();
        if (world == null) {
            return;
        }

        List<Block> farmlandBlocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    Location blockLocation = centerLocation.clone().add(x, y, z);
                    Block block = world.getBlockAt(blockLocation);
                    if (block.getType() == Material.FARMLAND) {
                        farmlandBlocks.add(block);
                    }
                }
            }
        }

        int batchSize = 600;
        int delayPerBatch = 10;

        new BukkitRunnable() {
            int batchIndex = 0;

            @Override
            public void run() {
                if (batchIndex * batchSize >= farmlandBlocks.size()) {
                    this.cancel();
                    return;
                }

                int start = batchIndex * batchSize;
                int end = Math.min(start + batchSize, farmlandBlocks.size());
                List<Block> batch = farmlandBlocks.subList(start, end);

                for (Block block : batch) {
                    BlockPosition blockPosition = new BlockPosition(
                        block.getX(),
                        block.getY(),
                        block.getZ()
                    );

                    BlockManager.placePlantation(player, blockPosition);
                    sendPacket(player, block);
                }

                batchIndex++;
            }
        }.runTaskTimer(Main.getInstance(), 0, delayPerBatch);
    }

    private void sendPacket(Player player, Block block) {
        try {
            Block blockAbove = block.getRelative(0, 1, 0);
            if (blockAbove.getType() != Material.AIR) {
                return;
            }

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

            BlockData blockData;

            if (AreaManager.getInstance().getAreaPlantation(player).equalsIgnoreCase("BEETROOTS")) {
                blockData = Bukkit.createBlockData("minecraft:" + AreaManager.getInstance().getAreaPlantation(player).toLowerCase() + "[age=3]");
            } else {
                blockData = Bukkit.createBlockData("minecraft:" + AreaManager.getInstance().getAreaPlantation(player).toLowerCase() + "[age=7]");
            }

            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(blockData);

            BlockPosition blockPosition = new BlockPosition(
                block.getX(),
                block.getY() + 1,
                block.getZ()
            );

            packet.getBlockData().write(0, wrappedBlockData);
            packet.getBlockPositionModifier().write(0, blockPosition);

            BlockManager.placePlantation(player, blockPosition);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar pacote: " + e.getMessage());
        }
    }

    public void growthDelay(Player player, Block block) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;

                if (ticks == 5) {
                    growth(player, block);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 20 * 5L, 20L);
    }

    public void growthDelay(Player player, Block block, int delay, int time) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;

                if (ticks == 5) {
                    growth(player, block);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), delay, 20L);
    }

    private void growth(Player player, Block block) {
        Block blockAbove = block.getRelative(0, 1, 0);
        if (blockAbove.getType() != Material.AIR) {
            return;
        }

        for (int age = 1; age <= 7; age++) {
            final int currentAge = age;

            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if (player.isOnline()) {

                            BlockData blockData;

                            if (AreaManager.getInstance().getAreaPlantation(player).equalsIgnoreCase("BEETROOTS")) {
                                blockData = Bukkit.createBlockData("minecraft:" + AreaManager.getInstance().getAreaPlantation(player).toLowerCase() + "[age=3]");
                            } else {
                                blockData = Bukkit.createBlockData("minecraft:" + AreaManager.getInstance().getAreaPlantation(player).toLowerCase() + "[age=" + currentAge + "]");
                            }
                            WrappedBlockData wheatState = WrappedBlockData.createData(blockData);

                            BlockPosition blockPosition = new BlockPosition(
                                block.getX(),
                                block.getY() + 1,
                                block.getZ()
                            );

                            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                            packet.getBlockData().write(0, wheatState);
                            packet.getBlockPositionModifier().write(0, blockPosition);

                            BlockManager.placePlantation(player, blockPosition);

                            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskLater(Main.getInstance(), currentAge * 20L);
        }
    }

    public static PlantationManager getInstance() {
        return instance;
    }
}
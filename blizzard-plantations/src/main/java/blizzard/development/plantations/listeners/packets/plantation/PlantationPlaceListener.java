package blizzard.development.plantations.listeners.packets.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class PlantationPlaceListener extends PacketAdapter {

    public PlantationPlaceListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Client.USE_ITEM
        ).optionAsync());
    }

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(4);

        if (block == null) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (playerCacheMethod.isInPlantation(player)) {

            if (!hasPersistentData(Main.getInstance(), item, "semente")) {
                if (player.hasPermission("*")) return;

                event.setCancelled(true);
            }

            String data = getPersistentData(Main.getInstance(), item, "semente");
            if (data == null) return;

            switch (data) {
                case "semente.batata":
                    sendPacket(player, block, "POTATOES");
                    growth(player, block, "POTATOES");
                    break;

                case "semente.cenoura":
                    sendPacket(player, block, "CARROTS");
                    growth(player, block, "CARROTS");
                    break;

                case "semente.tomate":
                    sendPacket(player, block, "BEETROOTS");
                    growth(player, block, "BEETROOTS");
                    break;

                case "semente.milho":
                    sendPacket(player, block, "WHEAT");
                    growth(player, block, "WHEAT");
                    break;
            }

        }

    }

    private void sendPacket(Player player, Block block, String crops) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
            BlockData blockData = null;

            switch (crops) {
                case "POTATOES" -> blockData = Bukkit.createBlockData("minecraft:potatoes");
                case "CARROTS" -> blockData = Bukkit.createBlockData("minecraft:carrots");
                case "BEETROOTS" -> blockData = Bukkit.createBlockData("minecraft:beetroots");
                case "WHEAT" -> blockData = Bukkit.createBlockData("minecraft:wheat");
            }
            WrappedBlockData wheatState = WrappedBlockData.createData(blockData);


            packet.getBlockData().write(0, wheatState);
            packet.getBlockPositionModifier().write(0, new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY() + 1,
                block.getLocation().getBlockZ()
            ));

            packet.setMeta("crops", true);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Falhou sendpéquet");
        }
    }

    private void growth(Player player, Block block, String crops) {
        for (int age = 1; age <= getMaxAge(crops); age++) {
            final int currentAge = age;

            Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                if (player.isOnline()) {
                    try {
                        BlockData blockData = null;
                        switch (crops) {
                            case "POTATOES" ->
                                blockData = Bukkit.createBlockData("minecraft:potatoes[age=" + Math.min(currentAge, 7) + "]");
                            case "CARROTS" ->
                                blockData = Bukkit.createBlockData("minecraft:carrots[age=" + Math.min(currentAge, 7) + "]");
                            case "BEETROOTS" ->
                                blockData = Bukkit.createBlockData("minecraft:beetroots[age=" + Math.min(currentAge, 3) + "]");
                            case "WHEAT" ->
                                blockData = Bukkit.createBlockData("minecraft:wheat[age=" + Math.min(currentAge, 7) + "]");
                        }
                        WrappedBlockData wheatState = WrappedBlockData.createData(blockData);

                        PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

                        packet.getBlockData().write(0, wheatState);
                        packet.getBlockPositionModifier().write(0, new BlockPosition(
                            block.getX(),
                            block.getY() + 1,
                            block.getZ()
                        ));

                        packet.setMeta("farmland", true);

                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, age * 20L);
        }
    }

    private int getMaxAge(String crops) {
        switch (crops) {
            case "BEETROOTS":
                return 3;
            case "POTATOES":
            case "CARROTS":
            case "WHEAT":
                return 7;
            default:
                return 7;
        }
    }
}

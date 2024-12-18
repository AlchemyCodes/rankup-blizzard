package blizzard.development.plantations.tasks;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.utils.packets.PacketUtils;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class PlantationRegenTask {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    public static void create(Block block, Player player, int time) {
        scheduler.runTaskLater(Main.getInstance(), () -> {
            PacketUtils.getInstance()
                .sendPacket(
                    player,
                    block
                );

        }, (time * 20L));

    }
}

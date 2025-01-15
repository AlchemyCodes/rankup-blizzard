package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.managers.BlockManager;
import blizzard.development.mine.mine.events.MineBlockBreakEvent;
import blizzard.development.mine.utils.packets.PacketUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MineBlockBreakListener implements Listener {

    @EventHandler
    public void onMineBlockBreak(MineBlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        final var event1 = new MineBlockBreakEvent(player, block);

        if (!BlockManager.isBlock(block.getX(), block.getY(), block.getZ())) {
            return;
        }

        if (event1.isCancelled()) {
            return;
        }

        PacketUtils.getInstance()
                        .sendAirBlock(
                                player, block
                        );
        player.sendActionBar("quebrado packet mina");
    }
}

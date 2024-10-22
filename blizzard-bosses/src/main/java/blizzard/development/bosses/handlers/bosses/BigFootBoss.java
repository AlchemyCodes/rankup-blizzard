package blizzard.development.bosses.handlers.bosses;

import blizzard.development.bosses.enums.Bosses;
import blizzard.development.bosses.utils.packets.npcs.NPC;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BigFootBoss {
    public static void spawn(Player player) {
        UUID uuid = UUID.randomUUID();
        NPC npc = new NPC(ProtocolLibrary.getProtocolManager(), uuid);

        Bosses boss = Bosses.BIGFOOT;
        String name = boss.getName();
        String value = boss.getValue();
        String signature = boss.getSignature();

        npc.spawn(player, "§bPé Grande", value, signature, null);
        npc.activateSecondLayer(player);
        player.setCompassTarget(null);
    }
}

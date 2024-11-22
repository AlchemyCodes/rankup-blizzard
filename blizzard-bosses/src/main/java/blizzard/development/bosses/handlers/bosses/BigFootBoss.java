package blizzard.development.bosses.handlers.bosses;

import blizzard.development.bosses.enums.Bosses;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BigFootBoss {
    public static void spawn(Player player) {
        UUID uuid = UUID.randomUUID();

        Bosses boss = Bosses.BIGFOOT;
        String name = boss.getName();
        String value = boss.getValue();
        String signature = boss.getSignature();

        player.setCompassTarget(null);
    }
}

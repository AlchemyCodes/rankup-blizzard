package blizzard.development.crates.factory;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface KeyHandlerInterface {
    void handle(Player player, ArmorStand armorStand);
}

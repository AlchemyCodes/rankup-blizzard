package blizzard.development.plantations.plantations.factory;

import org.bukkit.entity.Player;

public interface AreaFactory {

    void teleportToArea(Player player);

    void teleportToFriendArea(Player player, Player friend);
}

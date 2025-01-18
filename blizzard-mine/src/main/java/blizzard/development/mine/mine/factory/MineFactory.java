package blizzard.development.mine.mine.factory;

import org.bukkit.entity.Player;

public interface MineFactory {

    void sendToMine(Player player);
    void sendToExit(Player player);
}

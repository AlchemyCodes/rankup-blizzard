package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.managers.MineManager;
import blizzard.development.mine.mine.factory.MineFactory;
import blizzard.development.mine.utils.locations.LocationUtils;
import org.bukkit.entity.Player;

public class MineAdapter implements MineFactory {

    private static final MineAdapter instance = new MineAdapter();

    public static MineAdapter getInstance() {
        return instance;
    }

    @Override
    public void sendToMine(Player player) {
        player.teleport(
            LocationUtils.getMineSpawnLocation()
        );
    }

    public void generateMine(Player player) {
        MineManager.getInstance().transformArea(player);
    }
}

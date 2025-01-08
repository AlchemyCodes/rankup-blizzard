package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.mine.factory.MineFactory;
import org.bukkit.entity.Player;

public class MineAdapter implements MineFactory {

    private static final MineAdapter instance = new MineAdapter();
    public static MineAdapter getInstance() {
        return instance;
    }

    @Override
    public void sendToMine(Player player) {

    }
}

package blizzard.development.slimeworlds.api;

import blizzard.development.slimeworlds.managers.WorldManager;
import org.bukkit.entity.Player;

public class SlimeWorldAPI {

    private static SlimeWorldAPI instance;

    public void createWorld(Player player) throws Exception {
        WorldManager.getInstance().createWorld(player);
    }

    public static SlimeWorldAPI getInstance() {
        if (instance == null) instance = new SlimeWorldAPI();
        return instance;
    }
}

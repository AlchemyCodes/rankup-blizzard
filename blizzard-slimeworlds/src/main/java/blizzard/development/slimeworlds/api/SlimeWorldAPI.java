package blizzard.development.slimeworlds.api;

import blizzard.development.slimeworlds.managers.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SlimeWorldAPI {

    private static SlimeWorldAPI instance;

    public void createWorld(Player player) throws Exception {
        WorldManager.getInstance().createWorld(player);
    }

    public void teleportToWorld(Player player) {
        World world = Bukkit.getWorld("mundo_" + player.getName());

        if (world == null) return;

        Location location = new Location(world, 0, 100, 0);
        player.teleport(location);
    }

    public static SlimeWorldAPI getInstance() {
        if (instance == null) instance = new SlimeWorldAPI();
        return instance;
    }
}

package blizzard.development.essentials.listeners.player;

import blizzard.development.essentials.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) return;

        World world = Bukkit.getWorld(worldSpawn);

        player.teleport(
                new Location(
                        world,
                        x,
                        y,
                        z,
                        yaw,
                        pitch
                )
        );
        player.sendTitle(
                "§b§lBem vindo " + player.getName() + "!",
                "§7Sua jornada começa agora.",
                10,
                60,
                20
        );

    }
}

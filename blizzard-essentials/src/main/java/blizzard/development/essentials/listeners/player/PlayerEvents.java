package blizzard.development.essentials.listeners.player;

import blizzard.development.essentials.Main;
import blizzard.development.essentials.managers.BackManager;
import blizzard.development.essentials.managers.ViaVersionManager;
import blizzard.development.essentials.tasks.VersionTask;
import blizzard.development.essentials.utils.CooldownUtils;
import blizzard.development.essentials.utils.PluginImpl;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);

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

        String[] permissions = {
            "*",
            "alchemy.group.youtuber",
            "alchemy.group.suporte",
            "alchemy.group.alchemy",
            "alchemy.group.blizzard",
            "alchemy.group.esmeralda",
            "alchemy.group.diamante",
            "alchemy.group.ouro"
        };

        for (String permission : permissions) {
            if (player.hasPermission(permission)) {
                player.setAllowFlight(true);
                player.setFlying(true);

                player.sendMessage("");
                player.sendMessage(" §d§lEI! §dVocê é um dos incríveis jogadores VIP.");
                player.sendMessage(" §dsendo assim, você tem a §lpermissão§d de voar.");
                player.sendMessage("");
                break;
            }
        }

        if (ViaVersionManager.isBelowVersion(player, ProtocolVersion.v1_16_4)) {
            VersionTask versionTask = new VersionTask(player);
            versionTask.runTaskTimer(Main.getInstance(), 0L, 20);

            player.sendMessage("aaaaaaaaaa");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(null);
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getWorld().getName().equalsIgnoreCase("estufa")) {
            return;
        }

        if (CooldownUtils.getInstance().isInCountdown(player, "back")) {
            player.sendActionBar("§c§lEI! §cAguarde um pouco para voltar novamente.");
            player.playSound(player.getLocation(), "block.note_block_pling", 1, 1);
            return;
        }

        BackManager.add(player, event.getFrom());
    }

//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        Player player = event.getPlayer();
//        Location spawnLocation = player.getWorld().getSpawnLocation();
//
//        if (event.getTo().getY() <= 0) {
//            player.teleport(spawnLocation);
//            player.setFallDistance(0);
//        }
//    }

}

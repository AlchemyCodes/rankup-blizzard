package blizzard.development.essentials.listeners.player;

import blizzard.development.essentials.managers.BackManager;
import blizzard.development.essentials.utils.CooldownUtils;
import blizzard.development.essentials.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);

        Player player = event.getPlayer();

        player.teleport(Objects.requireNonNull(LocationUtils.getSpawnLocation()));
        player.sendTitle(
            "§d§lBem vindo " + player.getName() + "!",
            "§dSua jornada começa agora.",
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

        player.setFreezeTicks(2000);
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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getTo().getY() <= 0) {
            player.teleport(Objects.requireNonNull(LocationUtils.getSpawnLocation()));
            player.setFallDistance(0);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.deathMessage(null);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location location = LocationUtils.getSpawnLocation();
        if (location != null) {
            event.setRespawnLocation(location);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);

                Player killer = ((Player) event.getEntity()).getKiller();

                if (!(killer == null)) {
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.sendActionBar("§cO jogador " + player.getName() + " morreu em um combate contra " + killer.getName() + ".");
                    }
                }

                Location respawnLocation = LocationUtils.getSpawnLocation();
                if (respawnLocation != null) {
                    player.teleport(respawnLocation);
                }

                player.setHealth(20);
                player.setFoodLevel(20);
                player.setExp(0);

                player.playSound(player, Sound.ENTITY_VILLAGER_HURT, 0.4f, 0.4f);
            }
        }
    }

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event) {
        event.message(null);
    }

}

package blizzard.development.events.listeners;

import blizzard.development.events.managers.SumoManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.Objects;

public class SumoListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        SumoManager instance = SumoManager.getInstance();
        Player player = event.getPlayer();

        List<Player> players = instance.players;
        List<Player> isInGame = instance.isInGame;

        if (!instance.isSumoActive) return;

        if (!players.contains(player)) return;

        Material materialBelow = player.getLocation().getBlock().getType();

        if (materialBelow == Material.WATER || materialBelow == Material.KELP || materialBelow == Material.SEAGRASS) {
            players.remove(player);
            player.sendMessage("§cVocê foi eliminado do sumo!");
            player.teleport(Objects.requireNonNull(Bukkit.getWorld("spawn2")).getSpawnLocation());

            isInGame.clear();

            if (players.size() == 1) {
                Player winner = players.get(0);

                winner.teleport(Objects.requireNonNull(Bukkit.getWorld("spawn2")).getSpawnLocation());

                instance.sendMessageToAll("§aO jogador " + winner.getName() + " venceu o sumo!");
                instance.endSumo();
                return;
            }

            instance.startGames();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        SumoManager instance = SumoManager.getInstance();

        List<Player> players = instance.players;
        List<Player> isInGame = instance.isInGame;

        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            if (isInGame.contains(entity)) {
                event.setDamage(0);
            } else if (players.contains(entity)) {
                event.setCancelled(true);
            }
        }

    }
}

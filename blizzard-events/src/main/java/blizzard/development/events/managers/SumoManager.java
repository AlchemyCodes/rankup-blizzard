package blizzard.development.events.managers;

import blizzard.development.events.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SumoManager {

    public boolean isSumoActive = false;

    public List<Player> players = new ArrayList<>();
    public List<Player> isInGame = new ArrayList<>();

    private static SumoManager instance;

    public void startSumo() {
        isSumoActive = true;

        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        List<String> sumoStartMessage = messagesConfig.getStringList("events.sumo.sumoStart");

        for (Player player : Bukkit.getOnlinePlayers()) {
            sumoStartMessage.forEach(player::sendMessage);
        }

            new BukkitRunnable() {
                int time = 10;

                @Override
                public void run() {
                    if (time == 0) {
                        if (players.size() < 2) {
                            sendMessage(messagesConfig.getString("events.sumo.sumoNoPlayers"));
                            isSumoActive = false;
                            players.clear();
                            cancel();
                            return;
                        }

                        startGames();
                        cancel();
                        return;
                    }

                    sendTitle("§l§a" + time, "");

                    time--;
                }
            }.runTaskTimer(PluginImpl.getInstance().plugin, 0, 20);
    }

    public void startGames() {
        Collections.shuffle(players);

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        sendMessage("§a" + player1.getName() + " vs " + player2.getName());

        isInGame.add(player1);
        isInGame.add(player2);

        new BukkitRunnable() {
            @Override
            public void run() {
                player1.setGameMode(GameMode.SURVIVAL);
                player2.setGameMode(GameMode.SURVIVAL);
                teleportToSumo(player1, player2);
            }
        }.runTaskLater(PluginImpl.getInstance().plugin, 20 * 3);
    }

    private void teleportToSumo(Player player1, Player player2) {
        YamlConfiguration locationsConfig = PluginImpl.getInstance().Locations.getConfig();

        Location arenaLocation1 = new Location(
                Bukkit.getWorld(Objects.requireNonNull(
                        locationsConfig.getString("events.sumo.player1.world"))),
                locationsConfig.getDouble("events.sumo.player1.x"),
                locationsConfig.getDouble("events.sumo.player1.y"),
                locationsConfig.getDouble("events.sumo.player1.z"),
                (float) locationsConfig.getDouble("events.sumo.player1.yaw"),
                (float) locationsConfig.getDouble("events.sumo.player1.pitch"));

        Location arenaLocation2 = new Location(
                Bukkit.getWorld(Objects.requireNonNull(
                        locationsConfig.getString("events.sumo.player2.world"))),
                locationsConfig.getDouble("events.sumo.player2.x"),
                locationsConfig.getDouble("events.sumo.player2.y"),
                locationsConfig.getDouble("events.sumo.player2.z"),
                (float) locationsConfig.getDouble("events.sumo.player2.yaw"),
                (float) locationsConfig.getDouble("events.sumo.player2.pitch"));

        player1.teleport(arenaLocation1);
        player2.teleport(arenaLocation2);
    }

    public void endSumo() {
        players.clear();
        isSumoActive = false;
    }

    private void sendMessage(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    private void sendTitle(String title, String subtitle) {
        for (Player player : players) {
            player.sendTitle(title, subtitle);
        }
    }

    public void sendMessageToAll(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static SumoManager getInstance() {
        if (instance == null) {
            instance = new SumoManager();
        }
        return instance;
    }
}

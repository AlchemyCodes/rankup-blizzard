package blizzard.development.core.listener.geral;

import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.database.storage.PlayersData;
import blizzard.development.core.managers.BossBarManager;
import blizzard.development.core.tasks.TemperatureDecayTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {
    private final PlayersDAO database;

    public TrafficListener(PlayersDAO database) {
        this.database = database;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayersData playersData = this.database.findPlayerData(player.getUniqueId().toString());

        if (playersData == null) {
            playersData = new PlayersData(player.getUniqueId().toString(), player.getName(), 10.0D, ClothingType.COMMON);
            try {
                this.database.createPlayerData(playersData);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        PlayersCacheManager.cachePlayerData(player, playersData);
        BossBarManager.createBossBar(player);
        TemperatureDecayTask.startPlayerRunnable(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        BossBarManager.removeBossBar(player);
        TemperatureDecayTask.stopPlayerRunnable(player);
    }
}

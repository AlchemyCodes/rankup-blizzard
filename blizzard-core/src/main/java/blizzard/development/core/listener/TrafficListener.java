package blizzard.development.core.listener;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.clothing.adapters.CommonClothingAdapter;
import blizzard.development.core.clothing.adapters.LegendaryClothingAdapter;
import blizzard.development.core.clothing.adapters.MysticClothingAdapter;
import blizzard.development.core.clothing.adapters.RareClothingAdapter;
import blizzard.development.core.commands.ActiveCoreDebug;
import blizzard.development.core.database.cache.PlayersCacheManager;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.database.storage.PlayersData;
import blizzard.development.core.managers.GeneratorManager;
import blizzard.development.core.tasks.TemperatureDecayTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
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
            playersData = new PlayersData(player.getUniqueId().toString(), player.getName(), 10.0D, ClothingType.NONE);
            try {
                this.database.createPlayerData(playersData);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        PlayersCacheManager.getInstance().cachePlayerData(player, playersData);
        TemperatureDecayTask.startPlayerRunnable(player);

        switch (CoreAPI.getInstance().getPlayerClothing(player)) {
            case "COMMON" -> new CommonClothingAdapter().active(player);
            case "RARE" -> new RareClothingAdapter().active(player);
            case "MYSTIC" -> new MysticClothingAdapter().active(player);
            case "LEGENDARY" -> new LegendaryClothingAdapter().active(player);
        }

        ActiveCoreDebug.debugHashMap.put(player, false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        TemperatureDecayTask.stopPlayerRunnable(player);
    }
}

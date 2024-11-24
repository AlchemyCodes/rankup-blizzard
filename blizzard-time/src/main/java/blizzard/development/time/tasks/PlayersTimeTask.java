package blizzard.development.time.tasks;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.cache.setters.PlayersCacheSetters;
import blizzard.development.time.handlers.RewardsHandler;
import blizzard.development.time.utils.items.TextAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayersTimeTask extends BukkitRunnable {
    @Override
    public void run() {
        PlayersCacheManager.getInstance().playersCache.forEach((name, playersData) -> {
            Player player = Bukkit.getPlayer(name);
            if (player != null && player.isOnline()) {
                long updatedPlayTime = playersData.getPlayTime() + 1;
                PlayersCacheSetters.getInstance().setPlayTime(player, updatedPlayTime);
                checkAndNotifyMissions(player, updatedPlayTime);
            }
        });
    }

    private void checkAndNotifyMissions(Player player, long playTime) {
        PlayersCacheSetters setters = PlayersCacheSetters.getInstance();
        RewardsHandler handler = RewardsHandler.getInstance();
        List<Integer> missions = handler.getMissions();
        List<String> notifiedMissions = PlayersCacheGetters.getInstance().getNotifiedMissions(player);

        for (int missionId : missions) {
            if (!notifiedMissions.contains(String.valueOf(missionId))) {
                int missionTime = handler.getMissionTime(missionId);
                if (playTime >= missionTime) {

                    Component message = TextAPI.parse(
                            """ 
                             <bold><#55FF55>Mis<#55FF55><#9cff9c>são conc<#9cff9c><#55FF55>luída!<#55FF55></bold> §7[Tempo online]
                             §7Digite §7´§f/tempo§7´ para resgatar a sua §lrecompensa §8#%s
                            """.formatted(missionId)
                    );

                    player.sendMessage(message);

                    setters.addNotifiedMission(player, List.of(String.valueOf(missionId)));
                }
            }
        }
    }
}

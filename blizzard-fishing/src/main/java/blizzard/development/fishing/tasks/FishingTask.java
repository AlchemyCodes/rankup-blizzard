package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.fish.FishesUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class FishingTask implements Runnable {

    public FishingTask(Plugin plugin) {
        plugin.getServer()
                .getScheduler()
                .runTaskTimer(plugin, this, 0, 20L);
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Long> entry : FishingCacheManager.getFishermans().entrySet()) {

            final long lastFishTime = entry.getValue();

            if (lastFishTime + TimeUnit.SECONDS.toMillis(2) > System.currentTimeMillis()) {
                continue;
            }

            final UUID playerId = entry.getKey();
            final Player player = Bukkit.getPlayer(playerId);

            if (player != null) {
                fish(player);
                FishingCacheManager.resetPlayerLastFishTime(player);
            }
        }
    }

    private void fish(Player player) {
        int playerStrength = RodsCacheMethod.getInstance().getStrength(player);

        String rarity = FishesUtils.getRarity(playerStrength);

        List<String> availableFishes = FishesUtils.getFishes(rarity);

        if (!availableFishes.isEmpty()) {
            String caughtFish = availableFishes.get(new Random().nextInt(availableFishes.size()));

            FishesUtils.giveFish(player, caughtFish);
            player.sendMessage("§bVocê pescou um " + caughtFish + " de raridade " + rarity + "!");
        }
    }
}
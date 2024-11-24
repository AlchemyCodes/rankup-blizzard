package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.handlers.FishBucketHandler;
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
                giveFish(player);
                FishingCacheManager.resetPlayerLastFishTime(player);
            }
        }
    }

    private void giveFish(Player player) {
        PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();
        RodsCacheMethod rodsCacheMethod = RodsCacheMethod.getInstance();
        FishesUtils fishesUtils = FishesUtils.getInstance();

        int playerStrength = RodsCacheMethod.getInstance().getStrength(player);
        String rarity = fishesUtils.getRarity(playerStrength);

        if (cacheMethod.getFishes(player) >= cacheMethod.getStorage(player)) {
            player.sendMessage("§cBalde cheio");
            return;
        }

        if (fishesUtils.giveFrozenFish(player, cacheMethod)) {
            return;
        }

//        if (isSnowing) {
//            cacheMethod.setFrozenFish(player,cacheMethod.getFrozenFish(player) + 1);
//        }

        List<String> availableFishes = fishesUtils.getFishes(rarity);

        if (!availableFishes.isEmpty()) {
            String caughtFish = availableFishes.get(new Random().nextInt(availableFishes.size()));

            fishesUtils.giveFish(player, caughtFish, cacheMethod);
            fishesUtils.giveXp(player, rarity, rodsCacheMethod);
            FishBucketHandler.setBucket(player, 8);
            player.sendMessage("§bVocê pescou um " + caughtFish + " de raridade " + rarity + "!");
        }
    }
}
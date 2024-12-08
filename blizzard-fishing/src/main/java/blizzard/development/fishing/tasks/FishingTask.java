package blizzard.development.fishing.tasks;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
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
            int baseTaskTime = PluginImpl.getInstance().Config.getConfig().getInt("fishing.taskTime");

            int geyserTaskTime = EventsTask.isGeyserActive ? baseTaskTime / 2 : baseTaskTime;

            if (lastFishTime + TimeUnit.SECONDS.toMillis(geyserTaskTime) > System.currentTimeMillis()) {
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

        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

        int playerStrength = RodsCacheMethod.getInstance().getStrength(player);
        String rarity = fishesUtils.getRarity(playerStrength);

        if (cacheMethod.getFishes(player) >= cacheMethod.getStorage(player)) {
            player.sendMessage("§cBalde cheio");
            return;
        }

        if (fishesUtils.giveChanceFrozenFish(player, cacheMethod)) {
            return;
        }

        List<String> availableFishes = fishesUtils.getFishes(rarity);

        if (!availableFishes.isEmpty()) {
            String caughtFish = availableFishes.get(new Random().nextInt(availableFishes.size()));

            fishesUtils.giveFish(player, caughtFish, cacheMethod);
            fishesUtils.giveXp(player, rarity, rodsCacheMethod);
            double xp = fishesUtils.getXp(player, rarity, rodsCacheMethod);
            FishBucketHandler.setBucket(player, 8);

            String message = config.getString("pesca.action");
            if (message != null) {
                player.sendActionBar(message
                        .replace("{fishname}", caughtFish)
                        .replace("{xp}", String.valueOf(xp)));
            }

        }
    }
}
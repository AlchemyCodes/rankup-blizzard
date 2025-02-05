package blizzard.development.mine.database.cache.methods;

import blizzard.development.mine.database.cache.BoosterCacheManager;
import blizzard.development.mine.database.storage.BoosterData;
import blizzard.development.mine.mine.enums.BoosterEnum;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BoosterCacheMethods {
    private static final BoosterCacheMethods instance = new BoosterCacheMethods();

    public static BoosterCacheMethods getInstance() {
        return instance;
    }

    private final BoosterCacheManager boosterCacheManager = BoosterCacheManager.getInstance();

    public String getBoosterName(Player player) {
        BoosterData boosterData = boosterCacheManager.getBoosterData(player.getUniqueId());
        return boosterData != null ? boosterData.getBoosterName() : "";
    }

    public void setBoosterName(UUID uuid, String boosterName) {
        BoosterData boosterData = boosterCacheManager.getBoosterData(uuid);
        if (boosterData != null) {
            boosterData.setBoosterName(boosterName);
            boosterCacheManager.cacheBoosterData(uuid, boosterData);
        }
    }

    public Integer getBoosterDuration(UUID uuid) {
        BoosterData boosterData = boosterCacheManager.getBoosterData(uuid);
        return boosterData != null ? boosterData.getBoosterDuration() : 0;
    }

    public void setBoosterDuration(UUID uuid, int duration) {
        BoosterData boosterData = boosterCacheManager.getBoosterData(uuid);
        if (boosterData != null) {
            boosterData.setBoosterDuration(duration);
            boosterCacheManager.cacheBoosterData(uuid, boosterData);
        }
    }

    public Double getBoosterMultiplier(String boosterName) {
        for (BoosterEnum boosterEnum : BoosterEnum.values()) {
            if (boosterEnum.getName().equals(boosterName)) {
                return boosterEnum.getMultiplier();
            }
        }
        return 1.0;
    }

}

package blizzard.development.fishing.database.cache;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class FishingCacheManager {

    @Getter
    private static final Map<UUID, Long> fishermans = new HashMap<>();

    public static void resetPlayerLastFishTime(Player player) {
        fishermans.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public static boolean isFishing(Player player) {
        return fishermans.containsKey(player.getUniqueId());
    }

    public static void addFisherman(Player player) {
        if (isFishing(player)) {
            return;
        }

        addFisherman(player.getUniqueId());
    }

    public static void addFisherman(UUID uniqueId) {
        fishermans.put(uniqueId, System.currentTimeMillis());
    }

    public static void removeFisherman(UUID uniqueId) {
        fishermans.remove(uniqueId);
    }

}

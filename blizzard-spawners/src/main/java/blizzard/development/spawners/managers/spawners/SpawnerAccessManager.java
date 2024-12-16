package blizzard.development.spawners.managers.spawners;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SpawnerAccessManager {
    private static SpawnerAccessManager instance;
    private final Map<String, List<String>> inventoryAccessMap = new ConcurrentHashMap<>();

    public void addInventoryUser(String spawnerId, String userId) {
        inventoryAccessMap.computeIfAbsent(spawnerId, k -> new ArrayList<>()).add(userId);
    }

    public void removeInventoryUser(String spawnerId, String userId) {
        List<String> users = inventoryAccessMap.get(spawnerId);
        if (users != null) {
            users.remove(userId);
            if (users.isEmpty()) {
                inventoryAccessMap.remove(spawnerId);
            }
        }
    }

    public List<String> getInventoryUsers(String spawnerId) {
        return inventoryAccessMap.getOrDefault(spawnerId, List.of());
    }

    public static SpawnerAccessManager getInstance() {
        if (instance == null) {
            instance = new SpawnerAccessManager();
        }
        return instance;
    }
}

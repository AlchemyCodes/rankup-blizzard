package blizzard.development.spawners.methods;

import blizzard.development.spawners.database.cache.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.spawners.Spawners;
import org.bukkit.entity.Player;

public class SpawnersMethods {
    public static void createSpawner(Player player, String id, String location, Spawners spawner, Double amount) {
        String type = spawner.getType();
        String nickname = player.getName();
        SpawnersData spawnersData = new SpawnersData(
                id, type, amount, location, nickname
        );
        try {
            new SpawnersDAO().createSpawnerData(spawnersData);
            SpawnersCacheManager.getInstance().cacheSpawnerData(id, spawnersData);
        } catch (Exception ex) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            ex.printStackTrace();
        }
    }
}

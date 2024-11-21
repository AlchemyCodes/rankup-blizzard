package blizzard.development.spawners.methods;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.entity.Player;

public class SpawnersMethods {
    public static Boolean createSpawner(Player player, String id, String location, String mob_location, Spawners spawner, Double amount, String plotId) {
        String type = spawner.getType();
        String nickname = player.getName();
        SpawnersData spawnersData = new SpawnersData(
                id, type, amount, location, mob_location, nickname, plotId
        );
        try {
            new SpawnersDAO().createSpawnerData(spawnersData);
            SpawnersCacheManager.getInstance().cacheSpawnerData(id, spawnersData);
        } catch (Exception ex) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao salvar esse spawner no banco de dados!"));
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

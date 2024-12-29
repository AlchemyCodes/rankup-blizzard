package blizzard.development.monsters.monsters.handlers.monsters;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.entity.Player;

public class MonstersDataHandler {
    public static boolean createMonster(Player player, String id, String type, String location, Double life) {
        String owner = player.getName();

        MonstersData monstersData = new MonstersData(
                id,
                type,
                location,
                life,
                owner
        );

        try {
            new MonstersDAO().createMonsterData(monstersData);
            MonstersCacheManager.getInstance().cacheMonsterData(id, monstersData);
        } catch (Exception ex) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao salvar esse monstro no banco de dados!"));
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

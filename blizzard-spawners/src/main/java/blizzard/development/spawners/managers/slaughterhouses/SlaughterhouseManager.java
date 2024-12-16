package blizzard.development.spawners.managers.slaughterhouses;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.dao.SlaughterhouseDAO;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class SlaughterhouseManager {
    public static Boolean createSlaughterhouse(Player player, String id, String tier, String location, Double fuel, String state, String plotId, Integer friendsLimit) {
        String nickname = player.getName();
        SlaughterhouseData slaughterhouseData = new SlaughterhouseData(
                id,
                tier,
                location,
                nickname,
                fuel,
                state,
                plotId,
                new ArrayList<>(),
                friendsLimit
        );
        try {
            new SlaughterhouseDAO().createSlaughterhouseData(slaughterhouseData);
            SlaughterhouseCacheManager.getInstance().cacheSlaughterhouseData(id, slaughterhouseData);
        } catch (Exception ex) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao salvar esse abatedouro no banco de dados!"));
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

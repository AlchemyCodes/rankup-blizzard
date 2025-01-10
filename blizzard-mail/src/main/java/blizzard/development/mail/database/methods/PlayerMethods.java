package blizzard.development.mail.database.methods;

import blizzard.development.mail.database.cache.PlayerCacheManager;
import blizzard.development.mail.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerMethods {
    private static PlayerMethods instance;

    private PlayerCacheManager cacheManager = PlayerCacheManager.getInstance();

    public void addToList(Player player, List<String> itemName) {
        PlayerData data = PlayerCacheManager.getInstance().getPlayerData(player.getName());
        if (data != null) {
            List<String> items = data.getItems();
            items.addAll(itemName);
            data.setItems(items);
            cacheManager.cachePlayerData(player.getName(), data);
        }
    }

    public List<String> getItemList (Player player) {
        PlayerData data = PlayerCacheManager.getInstance().getPlayerData(player.getName());
        if (data != null) {
            return data.getItems();
        }
        return null;
    }


    public static PlayerMethods getInstance() {
        if (instance == null) {
            instance = new PlayerMethods();
        }
        return instance;
    }
}

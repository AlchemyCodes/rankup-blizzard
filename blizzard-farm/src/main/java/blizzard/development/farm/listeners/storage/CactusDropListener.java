package blizzard.development.farm.listeners.storage;

import blizzard.development.farm.database.StorageData;
import blizzard.development.farm.utils.apis.PlotSquaredAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CactusDropListener implements Listener {

    public static final Map<UUID, StorageData> plantations = new HashMap<>();

    public void cacheStorageData(Player player, StorageData playerData) {
        plantations.put(player.getUniqueId(), playerData);
    }
    public void cacheStorageData(UUID uuid, StorageData playerData) {
        plantations.put(uuid, playerData);
    }
    public static StorageData getStorageData(Player player) {
        return plantations.get(player.getUniqueId());
    }
    public void removeStorageData(Player player) {
        plantations.remove(player.getUniqueId());
    }



    @EventHandler
    public void onCactusPhysic(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        Player player = PlotSquaredAPI.getPlayerFromPlot(block);

        if (event.getChangedType() == Material.CACTUS) {

            setCactusAmount(player, 10);

            block.setType(Material.AIR);

            player.sendMessage("Quantia de cactos no armazem: " + getCactusAmount(player));
            event.setCancelled(true);
        }
    }

    public void setCactusAmount(Player player, int amount) {
        StorageData storageData = getStorageData(player);
        if (storageData != null) {
            storageData.setCactusStored(getCactusAmount(player) + amount);
            cacheStorageData(player, storageData);
        }
    }

    public static int getCactusAmount(Player player) {
        StorageData storageData = getStorageData(player);
        if (storageData != null) {
            return storageData.getCactusStored();
        }
        return 0;
    }
}

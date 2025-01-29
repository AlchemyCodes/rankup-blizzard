package blizzard.development.farm.database.cache.methods;

import blizzard.development.farm.database.cache.StorageCacheManager;
import blizzard.development.farm.database.storage.StorageData;
import org.bukkit.entity.Player;

public class StorageCacheMethod {

    private static final StorageCacheMethod instance = new StorageCacheMethod();
    public static StorageCacheMethod getInstance() {
        return instance;
    }

    public final StorageCacheManager storageCacheManager = StorageCacheManager.getInstance();

    public void setCarrotsAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setCarrotStored(amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public void addCarrotsAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setCarrotStored(getCarrotsAmount(player) + amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public int getCarrotsAmount(Player player) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            return storageData.getCarrotsStored();
        }
        return 0;
    }

    public void setPotatoesAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setPotatoStored(amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public void addPotatoesAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setPotatoStored(getPotatoesAmount(player) + amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public int getPotatoesAmount(Player player) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            return storageData.getPotatoesStored();
        }
        return 0;
    }

    public void setWheatAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setWheatStored(amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public void addWheatAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setWheatStored(getWheatAmount(player) + amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public int getWheatAmount(Player player) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            return storageData.getWheatStored();
        }
        return 0;
    }

    public void setMelonAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setMelonStored(amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public void addMelonAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setMelonStored(getMelonAmount(player) + amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public int getMelonAmount(Player player) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            return storageData.getMelonStored();
        }
        return 0;
    }

    public void setCactusAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setCactusStored(amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public void addCactusAmount(Player player, int amount) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            storageData.setCactusStored(getCactusAmount(player) + amount);
            storageCacheManager.cacheStorageData(player, storageData);
        }
    }

    public int getCactusAmount(Player player) {
        StorageData storageData = storageCacheManager.getStorageData(player);
        if (storageData != null) {
            return storageData.getCactusStored();
        }
        return 0;
    }
}

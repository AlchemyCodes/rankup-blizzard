package blizzard.development.essentials.database.cache.methods;

import blizzard.development.essentials.database.cache.HomeCacheManager;
import blizzard.development.essentials.database.storage.HomeData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static blizzard.development.essentials.utils.LocationUtils.deserializeLocation;
import static blizzard.development.essentials.utils.LocationUtils.getSerializedLocation;

public class HomeCacheMethod {

    private final HomeCacheManager homeCacheManager = HomeCacheManager.getInstance();

    public void setHome(Player player, String homeName) {
        String playerUUID = player.getUniqueId().toString();

        Map<String, HomeData> playerHomes = getPlayerHomes(playerUUID);

        Location location = player.getLocation();
        String locationString = getSerializedLocation(location);

        HomeData homeData = new HomeData(
            playerUUID,
            player.getName(),
            homeName.toLowerCase(),
            locationString
        );

        playerHomes.put(homeName.toLowerCase(), homeData);

        HomeCacheManager.cachePlayerData(playerUUID, homeData);

        player.sendActionBar("§d§lYAY! §dVocê criou a home §f´§7" + homeName + "§f´§d com sucesso.");
    }

    public boolean teleportToHome(Player player, String homeName) {
        String playerUUID = player.getUniqueId().toString();

        Map<String, HomeData> playerHomes = getPlayerHomes(playerUUID);

        HomeData homeData = playerHomes.get(homeName.toLowerCase());
        if (homeData == null) {
            player.sendMessage("§c§lEI! §cA home §f'§7" + homeName + "§f' §cnão existe.");
            return false;
        }

        Location homeLocation = deserializeLocation(homeData.getLocation());
        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendActionBar("§d§lYAY! §dVocê foi teleportado até a home §f´§7" + homeName + "§f´§d.");
            return true;
        }

        player.sendMessage("§c§lEI! §cNão foi possível teleportar para a home.");
        return false;
    }

    public void deleteHome(Player player, String homeName) {
        String playerUUID = player.getUniqueId().toString();

        Map<String, HomeData> playerHomes = getPlayerHomes(playerUUID);

        HomeData removedHome = playerHomes.remove(homeName.toLowerCase());
        if (removedHome != null) {
            player.sendMessage("§c§lEI! §cA Home §f'§7" + homeName + "§f'§c foi removida.");
        } else {
            player.sendMessage("§c§lEI! §cA home §f'§7" + homeName + "§f'§c não existe.");
        }
    }

    public Map<String, HomeData> listHomes(Player player) {
        String playerUUID = player.getUniqueId().toString();

        Map<String, HomeData> playerHomes = getPlayerHomes(playerUUID);

        if (playerHomes.isEmpty()) {
            player.sendMessage("§c§lEI! §cVocê não tem nenhuma home definida.");
            return new HashMap<>();
        }

        player.sendMessage("");
        player.sendMessage("§d Homes disponíveis:");
        playerHomes.keySet().forEach(home ->
            player.sendMessage("§8  ▶ §f" + home)
        );
        player.sendMessage("");

        return playerHomes;
    }

    private Map<String, HomeData> getPlayerHomes(String playerUUID) {
        HomeData existingHomeData = homeCacheManager.getPlayerDataByUUID(playerUUID);

        if (existingHomeData == null) {
            return new HashMap<>();
        }

        return HomeCacheManager.homeCache.values().stream()
            .filter(home -> home.getUuid().equals(playerUUID))
            .collect(Collectors.toMap(
                HomeData::getName,
                home -> home,
                (v1, v2) -> v1
            ));
    }

}
package blizzard.development.spawners.tasks.spawners.drops;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DropsAutoSellTask extends BukkitRunnable {
    private final SpawnersData spawnerData;

    public DropsAutoSellTask(SpawnersData spawnerData) {
        this.spawnerData = spawnerData;
    }

    @Override
    public void run() {
        final SpawnersHandler handler = SpawnersHandler.getInstance();
        final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
        final CurrenciesAPI api = CurrenciesAPI.getInstance();

        String nickname = spawnerData.getNickname();
        Player player = Bukkit.getPlayer(nickname);

        if (player != null && player.isOnline() && spawnerData.getAutoSellState()) {

            double drops = spawnerData.getDrops();
            double unitValue = handler.getSellDropPrice(
                    SpawnersUtils.getInstance().getSpawnerFromName(spawnerData.getType()).toString().toLowerCase()
            );

            if (drops <= 0) return;

            api.addBalance(player, Currencies.COINS, drops * unitValue);
            setters.setSpawnerDrops(spawnerData.getId(), 0);

            String formattedValue = NumberFormat.getInstance().formatNumber(drops * unitValue);

            player.sendActionBar(TextAPI.parse(
                    "§a§lYAY! §aOs drops de um gerador foi vendido por §2§l$§a§l" + formattedValue + "§7 (0% de bônus)§a."
            ));
        }
    }
}

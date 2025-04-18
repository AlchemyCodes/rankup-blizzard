package blizzard.development.fishing.tasks.items;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import blizzard.development.fishing.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FurnaceTask implements Runnable {

    private static final Map<Player, Integer> unfreezing = new HashMap<>();

    public FurnaceTask(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimer(main, this, 0,
                        20L);
    }

    public static void addPlayer(Player player) {
        unfreezing.put(player, 0);
    }

    public static void removePlayer(Player player) {
        unfreezing.remove(player);
        removeFish(player);
    }

    public static boolean isUnfreezing(Player player) {
       return unfreezing.containsKey(player);
    }


    @Override
    public void run() {
        for (Player player : unfreezing.keySet()) {
            Integer progress = unfreezing.get(player);

            PlayersCacheMethod playersCacheMethod = PlayersCacheMethod.getInstance();
            RodsCacheMethod rodsCacheMethod = RodsCacheMethod.getInstance();
            YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

            int coins = config.getInt("unfreeze.value");

            if (playersCacheMethod.getFrozenFish(player) == 0) {
                player.sendActionBar(config.getString("fornalha.naoTemPeixeCongelado"));
                removePlayer(player);
                return;
            }

            if (progress == null) {
                continue;
            }

            unfreezing.put(player, progress + 1);

            switch (progress) {
                case 2 -> {

                }

                case 3 -> {
                    player.getInventory().setItem(6, new ItemBuilder(Material.TROPICAL_FISH).build());
                }

                case 4 -> {
                    player.getInventory().setItem(6, null);
                    player.getInventory().setItem(7, new ItemBuilder(Material.TROPICAL_FISH).build());
                }

                case 5 -> {
                    player.getInventory().setItem(7, null);
                    removePlayer(player);
                    addPlayer(player);

                    FishesUtils fishesUtils = FishesUtils.getInstance();

                    String randomFish = fishesUtils.getRandomFish();
                    fishesUtils.giveFish(player, randomFish, PlayersCacheMethod.getInstance(), fishesUtils.fishQuantity(player, rodsCacheMethod));

                    playersCacheMethod.setFrozenFish(player, playersCacheMethod.getFrozenFish(player) - 1);
                    CurrenciesAPI.getInstance().addBalance(player, Currencies.COINS, coins);

                    randomFish = FishesUtils.getInstance().getFishName(randomFish);

                    String fishMessage = Objects.requireNonNull(config.getString("fornalha.descongelouPeixe"))
                            .replace("{fishname}", randomFish);
//                    String moneyMessage = Objects.requireNonNull(config.getString("fornalha.descongelouPeixeMoney"))
//                            .replace("{coins}", String.valueOf(coins));

                    player.sendMessage(fishMessage);
//                    player.sendMessage(moneyMessage);
                }

            }
        }
    }

    public static void removeFish(Player player) {
        player.getInventory().setItem(6, null);
        player.getInventory().setItem(7, null);
    }
}

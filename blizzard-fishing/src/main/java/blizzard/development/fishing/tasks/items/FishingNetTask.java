package blizzard.development.fishing.tasks.items;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.utils.Countdown;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FishingNetTask implements Runnable {

    private static final Map<UUID, Boolean> catchingTrashMap = new HashMap<>();

    public FishingNetTask(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(main, this, 0,
                        20L * PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments.trash.cooldown"));
    }

    public static boolean isCatchingTrash(Player player) {
        return catchingTrashMap.getOrDefault(player.getUniqueId(), false);
    }

    public static void removeCatchingTrash(Player player) {
        catchingTrashMap.remove(player.getUniqueId());
    }

    public static void setCatchingTrash(Player player, boolean isCatchingTrash) {
        catchingTrashMap.put(player.getUniqueId(), isCatchingTrash);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerId = player.getUniqueId();

            if (!catchingTrashMap.getOrDefault(playerId, false)) {
                continue;
            }

            PluginImpl instance = PluginImpl.getInstance();
            PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();

            YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();
            Random random = new Random();

            int chance = random.nextInt(101);
            int trashChance = instance.Enchantments.getInt("enchantments.trash.chance");
            int trashNecessary = instance.Enchantments.getInt("enchantments.trash.necessary");

            if (chance <= trashChance) {
                player.sendMessage("§8[DEBUG] §fNão veio nada");
                Countdown.getInstance().createCountdown(player, "trash",
                        instance.Enchantments.getInt("enchantments.trash.cooldown"),
                        TimeUnit.SECONDS);
                continue;
            }

            int currentTrash = cacheMethod.getTrash(player);

            if (currentTrash >= trashNecessary) {
                upRandomUpgrade(player);
                cacheMethod.setTrash(player, 0);
            } else {
                cacheMethod.setTrash(player, currentTrash + 1);
            }

            player.sendMessage("§8[DEBUG] §fVeio lixo");
            player.sendActionBar(config.getString("rede.achouLixo"));
            FishingNetHandler.setNet(player, 3);
        }
    }

    public void upRandomUpgrade(Player player) {
        Random random = new Random();
        int eventIndex = random.nextInt(3);

        RodsCacheMethod rodsCacheMethod = RodsCacheMethod.getInstance();

        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        YamlConfiguration enchantmentsConfig = PluginImpl.getInstance().Enchantments.getConfig();

        String baseMessage = Objects.requireNonNull(messagesConfig.getString("rede.melhorouEncantamento"));

        switch (eventIndex) {
            case 0 -> {
                if (rodsCacheMethod.getExperienced(player) >= enchantmentsConfig.getInt("enchantments.rod.especialista.maxlevel")) {
                    upRandomUpgrade(player);
                    FishingRodHandler.setRod(player, 0);
                    return;
                }

                String message = baseMessage.replace("{enchant}", "especialista");
                player.sendMessage(message);
            }
            case 1 -> {
                if (rodsCacheMethod.getExperienced(player) >= enchantmentsConfig.getInt("enchantments.rod.experiente.maxlevel")) {
                    upRandomUpgrade(player);
                    FishingRodHandler.setRod(player, 0);
                    return;
                }

                rodsCacheMethod.setExperienced(player, rodsCacheMethod.getExperienced(player) + 1);
                String message = baseMessage.replace("{enchant}", "experiente");
                player.sendMessage(message);
            }
            case 2 -> {
                if (rodsCacheMethod.getLucky(player) >= enchantmentsConfig.getInt("enchantments.rod.sortudo.maxlevel")) {
                    upRandomUpgrade(player);
                    FishingRodHandler.setRod(player, 0);
                    return;
                }

                rodsCacheMethod.setLucky(player, rodsCacheMethod.getLucky(player) + 1);
                String message = baseMessage.replace("{enchant}", "sortudo");
                player.sendMessage(message);
            }
        }
    }
}

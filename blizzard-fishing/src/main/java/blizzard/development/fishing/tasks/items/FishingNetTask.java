package blizzard.development.fishing.tasks.items;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.utils.Countdown;
import blizzard.development.fishing.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FishingNetTask implements Runnable {

    public static boolean isCatchingTrash = false;

    public FishingNetTask(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(main, this, 0,
                        20L * PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments.trash.cooldown"));
    }

    @Override
    public void run() {
        if (!(isCatchingTrash)) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            PluginImpl instance = PluginImpl.getInstance();
            PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();

            YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

            Random random = new Random();

            int chance = random.nextInt(101);

            if (chance <= instance.Enchantments.getInt("enchantments.trash.chance")) {
                player.sendMessage("§8[DEBUG] §fNão veio nada");
                Countdown.getInstance().createCountdown(player, "trash",
                        instance.Enchantments.getInt("enchantments.trash.cooldown"),
                        TimeUnit.SECONDS);
                return;
            }

            if (cacheMethod.getTrash(player) >= instance.Enchantments.getInt("enchantments.trash.necessary")) {
                upRandomUpgrade(player);
                cacheMethod.setTrash(player, 0);
            }

            player.sendMessage("§8[DEBUG] §fVeio lixo");
            player.sendActionBar(config.getString("rede.achouLixo"));
            cacheMethod.setTrash(player, cacheMethod.getTrash(player) + 1);

            FishingNetHandler.setNet(player, 3);
        }
    }

    public void upRandomUpgrade(Player player) {
        Random random = new Random();
        int eventIndex = random.nextInt(3);

        RodsCacheMethod instance = RodsCacheMethod.getInstance();
        YamlConfiguration config = PluginImpl.getInstance().Messages.getConfig();

        String baseMessage = Objects.requireNonNull(config.getString("rede.melhorouEncantamento"));

        switch (eventIndex) {
            case 0 -> {
                String message = baseMessage.replace("{enchant}", "x");
                player.sendMessage(message);
            }
            case 1 -> {
                instance.setExperienced(player, instance.getExperienced(player) + 1);
                String message = baseMessage.replace("{enchant}", "experiente");
                player.sendMessage(message);
            }
            case 2 -> {
                instance.setLucky(player, instance.getLucky(player) + 1);
                String message = baseMessage.replace("{enchant}", "sortudo");
                player.sendMessage(message);
            }
        }
    }
}

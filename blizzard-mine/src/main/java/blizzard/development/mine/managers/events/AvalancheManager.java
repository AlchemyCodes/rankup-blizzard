package blizzard.development.mine.managers.events;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

@CommandAlias("avalanche")
public class AvalancheManager extends BaseCommand {

    public static boolean isAvalancheActive = false;
    private final PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();
    private final int avalancheDuration = PluginImpl.getInstance().Config.getConfig().getInt("mine.avalanche");

    @Default
    public void onAvalanche() {
        if (isAvalancheActive) return;

        isAvalancheActive = true;
        startSnowfall();
        startAvalanche();
    }

    private void startAvalanche() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (cacheMethods.isInMine(player)) {
                applyAvalanche(player);
            } else {
                notifyPlayers(player);
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                stopAvalanche();
            }
        }.runTaskLater(Main.getInstance(), 20L * avalancheDuration);
    }

    private void applyAvalanche(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 8));
        player.sendTitle("§bVocê foi pego por uma avalanche!", "§fAproveite a neve e ganhe bônus na mineração.");
        MineAdapter.getInstance().resendToMine(player);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> MineAdapter.getInstance().generateMine(player));
    }

    private void notifyPlayers(Player player) {
        player.sendMessage(" ", "§bMINA! §fUma avalanche cobriu a mina com neve, aproveite para ganhar bônus em sua mineração!", " ");
    }

    private void stopAvalanche() {
        isAvalancheActive = false;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("§bMINA! §fA neve derreteu e tudo voltou ao normal...");
            if (cacheMethods.isInMine(player)) {
                MineAdapter.getInstance().resendToMine(player);
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> MineAdapter.getInstance().generateMine(player));
            }
        }
    }

    private void startSnowfall() {
        World world = Bukkit.getWorld("Mina");
        if (world == null) return;

        int minX = 1005, maxX = 1138;
        int minZ = 920, maxZ = 1040;
        int y = 130;
        Random random = new Random();

        new BukkitRunnable() {
            int waves = 0;

            @Override
            public void run() {
                if (!isAvalancheActive || waves >= 60) {
                    this.cancel();
                    return;
                }

                for (int i = 0; i < 200; i++) {
                    int x = minX + random.nextInt(maxX - minX + 1);
                    int z = minZ + random.nextInt(maxZ - minZ + 1);
                    Location spawnLocation = new Location(world, x, y, z);
                    world.spawn(spawnLocation, Snowball.class);
                }
                waves++;
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 10L);
    }
}

package blizzard.development.mine.managers.events;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@CommandAlias("avalanche")
public class AvalancheManager extends BaseCommand {

    public static boolean isAvalancheActive = false;

    @Default
    public void onAvalanche() {
        PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();
        int avalancheTime = PluginImpl.getInstance().Config.getConfig().getInt("mine.avalanche");

        isAvalancheActive = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (cacheMethods.isInMine(player)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 8));
                player.sendTitle("§bVocê foi pego por uma avalanche!", "§faproveite a neve e ganhe bônus na mineração.");
                MineAdapter.getInstance().resendToMine(player);
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> MineAdapter.getInstance().generateMine(player));
            } else {
                player.sendMessage(" ", "§bMINA! §fUma avalanche cobriu a mina com neve, aproveite para ganhar bônus em sua mineração!", " ");
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    isAvalancheActive = false;
                    player.sendMessage("§bMINA! §fA neve derreteu e tudo voltou ao normal...");
                    if (!cacheMethods.isInMine(player)) return;
                    MineAdapter.getInstance().resendToMine(player);
                    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> MineAdapter.getInstance().generateMine(player));
                }
             }.runTaskLater(Main.getInstance(),
                    20L * avalancheTime);
        }
    }
}

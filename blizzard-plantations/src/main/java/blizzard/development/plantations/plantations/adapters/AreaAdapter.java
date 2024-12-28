package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.managers.upgrades.agility.AgilityManager;
import blizzard.development.plantations.plantations.factory.AreaFactory;
import blizzard.development.plantations.utils.LocationUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;

public class AreaAdapter implements AreaFactory {

    private static final AreaAdapter instance = new AreaAdapter();
    public static AreaAdapter getInstance() {
        return instance;
    }

    @Override
    public void teleportToArea(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        Location location = LocationUtils.getPlantationSpawnLocation();

        if (location == null) {
            player.sendActionBar("§c§lEI! §cO spawn da estufa ainda não foi setado.");
            return;
        }

        new BukkitRunnable() {

            int i = 0;
            @Override
            public void run() {
                i++;

                if (i == 2) {
                    player.teleport(location);
                    String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");
                    AgilityManager.check(player, id);
                }

                if (i == 5) {

                    player.sendTitle("§a§lEstufa!", "§aVocê entrou na estufa.", 10, 70, 20);

                    for (Player players : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(Main.getInstance(), players);
                    }

                    PlayerCacheMethod.
                        getInstance()
                        .setInPlantation(player);

                    PlantationManager
                        .getInstance()
                        .transform(
                            player,
                            AreaManager.getInstance().getArea(player)
                        );

                    player.clearActivePotionEffects();
                    this.cancel();
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 8));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 100000));

//                if (player.hasPermission("*")) {
//                    player.clearActivePotionEffects();
//                }

                player.showTitle(
                    Title.title(
                        Component.text("§c§lAguarde!"),
                        Component.text("§cEstamos carregando a sua área."),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
                    )
                );

                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 5L, 1L);
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);

    }

    @Override
    public void teleportToFriendArea(Player player, Player friend) {

        ItemStack item = player.getInventory().getItemInMainHand();
        Location location = LocationUtils.getPlantationSpawnLocation();

        if (location == null) {
            player.sendActionBar("§c§lEI! §cO spawn da estufa ainda não foi setado.");
            return;
        }

        new BukkitRunnable() {

            int i = 0;
            @Override
            public void run() {
                i++;

                if (i == 5) {
                    player.teleport(location);
                    String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");
                    AgilityManager.check(player, id);
                }

                if (i == 10) {

                    player.sendTitle("§a§lEstufa!", "§aVocê entrou na estufa do jogador " + friend.getName(), 10, 70, 20);

                    for (Player players : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(Main.getInstance(), players);
                        friend.hidePlayer(Main.getInstance(), players);
                        player.showPlayer(Main.getInstance(), friend);
                        friend.showPlayer(Main.getInstance(), player);
                    }

                    PlayerCacheMethod.
                        getInstance()
                        .setInPlantation(player);

                    PlantationManager
                        .getInstance()
                        .transformWithFriend(
                            player,
                            friend,
                            AreaManager.getInstance().getArea(friend)
                        );

                    player.clearActivePotionEffects();
                    this.cancel();
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 8));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 100000));

                if (player.hasPermission("*")) {
                    player.clearActivePotionEffects();
                }

                player.showTitle(
                    Title.title(
                        Component.text("§c§lAguarde!"),
                        Component.text("§cEstamos carregando a área do jogador " + friend.getName()),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
                    )
                );

                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 5L, 1L);
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);


    }


}

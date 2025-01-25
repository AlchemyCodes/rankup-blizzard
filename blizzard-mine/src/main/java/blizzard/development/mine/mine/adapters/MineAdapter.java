package blizzard.development.mine.mine.adapters;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.managers.mine.DisplayManager;
import blizzard.development.mine.managers.mine.MineManager;
import blizzard.development.mine.managers.mine.NPCManager;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.mine.factory.MineFactory;
import blizzard.development.mine.utils.locations.LocationUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

public class MineAdapter implements MineFactory {

    private static final MineAdapter instance = new MineAdapter();
    public static MineAdapter getInstance() {
        return instance;
    }

    private final ToolAdapter toolAdapter = ToolAdapter.getInstance();

    @Override
    public void sendToMine(Player player) {
        Location spawnLocation = LocationUtils.getLocation(LocationEnum.SPAWN.getName());

        if (spawnLocation == null) {
            player.sendActionBar("§c§lEI! §cO spawn da mina ainda não foi setado.");
            return;
        }

        if (!isInventoryEmpty(player)) {
            player.sendActionBar("§c§lEI! §cVocê precisa estar com o inventário vazio para entrar na mina.");
            return;
        }

        player.teleport(spawnLocation);

        manageTool(player, false);
        manageVisibility(player, false);
        PlayerCacheMethods.getInstance().setInMine(player);

        startTask(player);
    }

    public void resendToMine(Player player) {
        Location spawnLocation = LocationUtils.getLocation(LocationEnum.SPAWN.getName());
        player.teleport(spawnLocation);
        manageTool(player, false);
    }

    @Override
    public void sendToExit(Player player) {
        Location exitLocation = LocationUtils.getLocation(LocationEnum.EXIT.getName());

        if (exitLocation == null) {
            player.sendActionBar("§c§lEI! §cA saída da mina ainda não foi setado.");
            return;
        }

        player.teleport(exitLocation);
        player.sendTitle("§e§lMina!", "§eVocê saiu da mina.", 10, 70, 20);

        manageVisibility(player, true);
        manageTool(player, true);
        PlayerCacheMethods.getInstance().removeFromMine(player);
    }

    private void startTask(Player player) {
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == 1) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                        generateMine(player);

                        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                            Location npcLocation = LocationUtils.getLocation(LocationEnum.NPC.getName());
                            if (npcLocation != null) {
                                UUID uuid = NPCManager.getInstance().spawnNPC(player, npcLocation);
                                HologramBuilder.getInstance().createHologram(
                                        player,
                                        uuid,
                                        npcLocation.add(0.0, 2.6, 0.0),
                                        Arrays.asList(
                                                "§e§lMINERAÇÃO!",
                                                "§bClique para gerenciar."
                                        ),
                                    true
                                );
                            }
                        });
                    }, 20L);
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 8));
                player.showTitle(
                        Title.title(
                                Component.text("§c§lAguarde!"),
                                Component.text("§cEstamos carregando a sua mina."),
                                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
                        )
                );
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 5L, 1L);

                i++;
                if (i == 4) {
                    player.clearActivePotionEffects();
                    player.sendTitle("§e§lMina!", "§eVocê entrou na mina.", 10, 70, 20);
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 5L, 1L);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1));
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 25L);
    }

    public void generateMine(Player player) {
        MineManager.getInstance().transformArea(player);
    }

    private void manageTool(Player player, Boolean state) {
        if (state) {
            toolAdapter
                    .removeTool(player);
        } else {
            toolAdapter
                    .giveTool(player);
        }
    }

    private void manageVisibility(Player player, Boolean state) {
        if (state) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                player.showPlayer(Main.getInstance(), players);
            }

            for (Player players : Bukkit.getOnlinePlayers()) {
                players.showPlayer(Main.getInstance(), player);
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(Main.getInstance(), players);
            }

            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(Main.getInstance(), player);
            }
        }
    }

    private Boolean isInventoryEmpty(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }
}
package blizzard.development.mine.mine.adapters;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MineAdapter implements MineFactory {

    private static final MineAdapter instance = new MineAdapter();
    private final ToolAdapter toolAdapter = ToolAdapter.getInstance();
    private final Map<UUID, BukkitRunnable> playerTasks = new HashMap<>();

    public static MineAdapter getInstance() {
        return instance;
    }

    @Override
    public void sendToMine(Player player) {
        Location spawnLocation = LocationUtils.getLocation(LocationEnum.SPAWN.getName());

        if (spawnLocation == null) {
            player.sendActionBar(Component.text("§c§lEI! §cO spawn da mina ainda não foi setado."));
            return;
        }

        if (!isInventoryEmpty(player)) {
            player.sendActionBar(Component.text("§c§lEI! §cVocê precisa estar com o inventário vazio para entrar na mina."));
            return;
        }

        player.teleport(spawnLocation);
        setupPlayerForMine(player);
        initializeMineSequence(player);
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
            player.sendActionBar(Component.text("§c§lEI! §cA saída da mina ainda não foi setada."));
            return;
        }

        cleanupPlayerExit(player, exitLocation);
    }

    public void resetMine(Player player) {
        Location spawnLocation = LocationUtils.getLocation(LocationEnum.SPAWN.getName());

        if (spawnLocation == null) {
            player.sendActionBar(Component.text("§c§lEI! §cO spawn da mina ainda não foi setado."));
            return;
        }

        executeResetSequence(player, spawnLocation);
    }

    private void setupPlayerForMine(Player player) {
        manageTool(player, false);
        manageVisibility(player, false);
        PlayerCacheMethods.getInstance().setInMine(player);
        player.getInventory().setHeldItemSlot(4);
    }

    private void initializeMineSequence(Player player) {
        BukkitRunnable task = new BukkitRunnable() {
            private int step = 0;

            @Override
            public void run() {
                if (step == 1) {
                    CompletableFuture.runAsync(() -> {
                        generateMine(player);
                        spawnNPCAndHologram(player);
                    });
                }

                updatePlayerEffects(player);
                step++;

                if (step == 4) {
                    finalizeInitialization(player);
                    this.cancel();
                    playerTasks.remove(player.getUniqueId());
                }
            }
        };

        task.runTaskTimer(Main.getInstance(), 0L, 25L);
        playerTasks.put(player.getUniqueId(), task);
    }

    private void spawnNPCAndHologram(Player player) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Location npcLocation = LocationUtils.getLocation(LocationEnum.NPC.getName());
            if (npcLocation != null) {
                UUID uuid = NPCManager.getInstance().spawnNPC(player, npcLocation);
                createHologram(player, uuid, npcLocation);
            }
        });
    }

    private void createHologram(Player player, UUID uuid, Location location) {
        HologramBuilder.getInstance().createPlayerHologram(
                player,
                uuid,
                location.add(0.0, 2.6, 0.0),
                Arrays.asList(
                        "§e§lMINERAÇÃO!",
                        "§bClique para gerenciar."
                )
        );
    }

    private void updatePlayerEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 8));
        player.showTitle(Title.title(
                Component.text("§c§lAguarde!"),
                Component.text("§cEstamos carregando a sua mina."),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
        ));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 1F);
    }

    private void finalizeInitialization(Player player) {
        player.clearActivePotionEffects();
        player.sendTitle("§e§lMina!", "§eVocê entrou na mina.", 10, 70, 20);
        player.getInventory().setHeldItemSlot(4);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5F, 1F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1));

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            PodiumAdapter.getInstance().createAllNPCs();
            ExtractorAdapter.getInstance().createExtractor(player);
        }, 100L);
    }

    private void cleanupPlayerExit(Player player, Location exitLocation) {
        player.teleport(exitLocation);
        player.sendTitle("§e§lMina!", "§eVocê saiu da mina.", 10, 70, 20);

        manageVisibility(player, true);
        manageTool(player, true);
        PlayerCacheMethods.getInstance().removeFromMine(player);
        cancelTask(player);
    }

    private void executeResetSequence(Player player, Location spawnLocation) {
        player.teleport(spawnLocation);
        applyResetEffects(player);

        CompletableFuture.runAsync(() -> {
            generateMine(player);
            finalizeReset(player);
        });
    }

    private void applyResetEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 8));
        player.showTitle(Title.title(
                Component.text("§c§lAguarde!"),
                Component.text("§cSua mina está sendo resetada."),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(3))
        ));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5F, 1F);
    }

    private void finalizeReset(Player player) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            player.clearActivePotionEffects();
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1));
            player.sendTitle("§e§lMina!", "§eSua mina foi resetada com sucesso!", 10, 70, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5F, 1F);
        });
    }

    public void generateMine(Player player) {
        MineManager.getInstance().transformArea(player);
    }

    private void manageTool(Player player, Boolean state) {
        if (state) {
            toolAdapter.removeTool(player);
        } else {
            toolAdapter.giveTool(player);
        }
    }

    private void manageVisibility(Player player, Boolean state) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (state) {
                player.showPlayer(Main.getInstance(), onlinePlayer);
                onlinePlayer.showPlayer(Main.getInstance(), player);
            } else {
                player.hidePlayer(Main.getInstance(), onlinePlayer);
                onlinePlayer.hidePlayer(Main.getInstance(), player);
            }
        }
    }

    private Boolean isInventoryEmpty(Player player) {
        if (Arrays.stream(player.getInventory().getContents())
                .anyMatch(item -> item != null && item.getType() != Material.AIR)) {
            return false;
        }
        return Arrays.stream(player.getInventory().getArmorContents())
                .allMatch(item -> item == null || item.getType() == Material.AIR);
    }

    private void cancelTask(Player player) {
        BukkitRunnable task = playerTasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }
}
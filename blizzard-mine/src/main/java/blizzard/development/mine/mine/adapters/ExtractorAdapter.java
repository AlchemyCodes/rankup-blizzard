package blizzard.development.mine.mine.adapters;

import blizzard.development.core.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.mine.builders.display.ExtractorBuilder;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.mine.factory.ExtractorFactory;
import blizzard.development.mine.tasks.mine.ExtractorUpdateTask;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import blizzard.development.mine.utils.text.NumberUtils;
import blizzard.development.mine.utils.text.ProgressBarUtils;
import blizzard.development.mine.utils.text.TextUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

public class ExtractorAdapter implements ExtractorFactory {
    private static final ExtractorAdapter instance = new ExtractorAdapter();
    public static ExtractorAdapter getInstance() {
        return instance;
    }

    private final HashMap<Player, Integer> extractorBlocks = new HashMap<>();

    @Override
    public void activeExtractor(Player player, Block block, double money, double blocks) {
        int currentBlocks = getExtractorBlocks(player) + 1;
        extractorBlocks.put(player, currentBlocks);
        if (currentBlocks >= 200) {
            giveExtractorRewards(player, block, blocks, money);
            extractorBlocks.put(player, 0);
        }
    }

    public int getExtractorBlocks(Player player) {
        return extractorBlocks.getOrDefault(player, 0);
    }

    private void giveExtractorRewards(Player player, Block block, double blocks, double money) {
        Location effectLocation = block.getLocation().add(0.5, 0.5, 0.5);
        player.playSound(effectLocation, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7f, 1.5f);

        player.showTitle(
                Title.title(
                        TextUtils.parse("§d§lEXTRATORA!"),
                        TextUtils.parse("§dObjetivo concluído."),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
        );


        new BukkitRunnable() {
            private int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 40) {
                    this.cancel();
                    return;
                }

                double progress = (double) ticks / 40;
                double maxHeight = 2.5;
                double height = progress * maxHeight;

                double radius = 1.2;
                for (int i = 0; i < 2; i++) {
                    double angle = (ticks * 0.5) + (i * Math.PI);
                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);

                    Location particleLoc = effectLocation.clone().add(x, height, z);
                    player.spawnParticle(Particle.SPELL_WITCH, particleLoc, 1, 0, 0, 0, 0);
                    player.spawnParticle(Particle.PORTAL, particleLoc, 1, 0, 0, 0, 0.05);
                }

                if (ticks % 5 == 0) {
                    player.playSound(effectLocation, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.5f, (float) (1.0f + (progress * 0.5f)));
                }

                ticks++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);

        int brokenBlocks = breakBlocksInRadius(player, block.getLocation());
        double blocksPrice = blocks * brokenBlocks;
        double moneyToAdd = money * brokenBlocks;

        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        currenciesAPI.addBalance(player, Currencies.COINS, moneyToAdd);
        currenciesAPI.addBalance(player, Currencies.BLOCKS, blocksPrice);

        String formattedMoney = NumberUtils.getInstance().formatNumber(moneyToAdd);
        String formattedBlocks = NumberUtils.getInstance().formatNumber(brokenBlocks);

        player.sendMessage(TextUtils.parse(" <bold><#FF55FF>Ext<#ff6bff><#ff6bff>rat<#ff6bff><#ff6bff>ora!<#FF55FF></bold> " +
                "§8✈ §f§l+§a" + formattedMoney + "§l$ §7♦ §fBlocos: §7" + formattedBlocks));
    }

    private int breakBlocksInRadius(Player player, Location center) {
        int radius = 15;
        int startX = center.getBlockX() - radius;
        int endX = center.getBlockX() + radius;
        int startY = Math.max(center.getBlockY() - radius, 0);
        int endY = Math.min(center.getBlockY() + radius, center.getWorld().getMaxHeight());
        int startZ = center.getBlockZ() - radius;
        int endZ = center.getBlockZ() + radius;
        int blockCount = 0;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = center.getWorld().getBlockAt(x, y, z);
                    if (BlockManager.getInstance().hasBlock(x, y, z)) {
                        blockCount++;
                        MinePacketUtils.getInstance().sendAirBlock(player, block);
                    }
                }
            }
        }
        return blockCount;
    }

    public void createExtractor(Player player) {
        Location location = LocationUtils.getLocation(LocationEnum.EXTRACTOR_NPC.getName());
        if (location != null) {
            ExtractorBuilder.getInstance().initializeExtractor(location);
            HologramBuilder.getInstance().removeHologram(player.getUniqueId());
            HologramBuilder.getInstance().createPlayerHologram(
                    player,
                    player.getUniqueId(),
                    location.add(0, 4.6, 0),
                    Arrays.asList(
                            "§e§lEXTRATORA!",
                            "§fQuebre blocos para conseguir",
                            "§fativar o poder da extratora.",
                            "",
                            " §bProgresso:",
                            " " + ProgressBarUtils.getInstance().extractor(player),
                            ""
                    )
            );
        }
        ExtractorUpdateTask extractorUpdateTask = new ExtractorUpdateTask();
        BukkitTask task = extractorUpdateTask.runTaskTimer(Main.getInstance(), 20L * 10, 20L * 10);
        extractorUpdateTask.setTask(task);
    }
}
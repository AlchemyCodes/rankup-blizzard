package blizzard.development.excavation.managers.upgrades.extractor;

import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.tasks.BlockRegenTask;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ExtractorBreakEffect {
    private final JavaPlugin plugin;
    private final Random random;
    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final Map<Player, List<Block>> brokenBlocksMap = new HashMap<>(); // Map para armazenar listas de blocos quebrados por jogador

    public ExtractorBreakEffect(JavaPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    public void startExcavatorBreak(Block centerBlock, Player player, int radius, int depth) {
        List<Block> affectedBlocks = getBlocksInCone(centerBlock, radius, depth);
        brokenBlocksMap.putIfAbsent(player, new ArrayList<>()); // Inicializa a lista de blocos para o jogador, se não existir

        player.playSound(centerBlock.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0f, 0.5f);

        new BukkitRunnable() {
            double time = 0;
            int blockIndex = 0;
            int totalBlocksBroken = 0;
            final int BLOCKS_PER_TICK = 5;
            final double ANIMATION_DURATION = 2.0;

            @Override
            public void run() {
                if (time >= ANIMATION_DURATION || blockIndex >= affectedBlocks.size()) {
                    if (totalBlocksBroken > 0) {
                        playerCacheMethod.setBlocks(player, playerCacheMethod.getBlocks(player) + totalBlocksBroken);

                        List<Block> playerBrokenBlocks = brokenBlocksMap.get(player);

                        playerBrokenBlocks.forEach(block -> {
                            BlockRegenTask.create(block, Material.COARSE_DIRT, 5);
                        });

                        playerBrokenBlocks.clear();
                    }
                    cancel();
                    return;
                }

                int blocksBrokenThisTick = 0;

                for (int i = 0; i < BLOCKS_PER_TICK && blockIndex < affectedBlocks.size(); i++) {
                    Block block = affectedBlocks.get(blockIndex++);
                    if (block.getType() != Material.AIR) {
                        block.setType(Material.AIR);
                        blocksBrokenThisTick++;
                        totalBlocksBroken++;

                        // Adiciona o bloco à lista de blocos quebrados do jogador
                        brokenBlocksMap.get(player).add(block);

                        if (random.nextInt(4) == 0) {
                            spawnEffectParticles(player, block.getLocation());
                        }
                    }
                }

                time += 0.05;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void spawnEffectParticles(Player player, Location location) {
        player.spawnParticle(Particle.ELECTRIC_SPARK, location, 3, 0.2, 0.2, 0.2, 0.05);
    }

    private List<Block> getBlocksInCone(Block center, int radius, int depth) {
        List<Block> blocks = new ArrayList<>();
        for (int y = 0; y >= -depth; y--) {
            int layerRadius = Math.max(1, radius * (depth + y) / depth);
            for (int x = -layerRadius; x <= layerRadius; x++) {
                for (int z = -layerRadius; z <= layerRadius; z++) {
                    if (x * x + z * z <= layerRadius * layerRadius) {
                        Block block = center.getRelative(x, y, z);
                        if (block.getType() != Material.AIR) {
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        blocks.sort((b1, b2) -> Integer.compare(b2.getY(), b1.getY()));
        return blocks;
    }
}

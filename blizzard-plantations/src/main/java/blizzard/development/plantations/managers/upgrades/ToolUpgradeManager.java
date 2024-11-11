package blizzard.development.plantations.managers.upgrades;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;

public class ToolUpgradeManager {

    public static PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    public static ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player) {
        int totalBlocks = playerCacheMethod.getPlantations(player);

        if (totalBlocks >= 300) {
            randomEnchant(player);
            playerCacheMethod.setPlantations(player, 0);
        }
    }

    public static void checkPlowingTool(Player player) {
        int totalBlocks = playerCacheMethod.getPlantations(player);

        if (totalBlocks >= 300) {
            randomPlowingToolEnchant(player);
            playerCacheMethod.setPlantations(player, 0);
        }
    }


    private static void randomPlowingToolEnchant(Player player) {
        int upgrade = ThreadLocalRandom.current().nextInt(1, 2);

        ItemStack item = player.getInventory().getItemInMainHand();
        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        int currentAccelerator = toolCacheMethod.getAccelerator(id);
        int currentPlow = toolCacheMethod.getAccelerator(id);

        switch (upgrade) {
            case 1:
                toolCacheMethod.setAccelerator(id, currentAccelerator + 1);
                player.sendTitle(
                        "§3§lACELERADOR!",
                        "§3O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
            break;
            case 2:
                toolCacheMethod.setAccelerator(id, currentPlow + 1);
                player.sendTitle(
                        "§c§lARADOR!",
                        "§cO encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
        }

    }

    private static void randomEnchant(Player player) {
        int upgrade = ThreadLocalRandom.current().nextInt(1, 4);

        ItemStack item = player.getInventory().getItemInMainHand();
        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        int currentBotany = toolCacheMethod.getBotany(id);
        int currentAgility = toolCacheMethod.getAgility(id);
        int currentExplosion = toolCacheMethod.getExplosion(id);
        int currentAccelerator = toolCacheMethod.getAccelerator(id);
        int currentPlow = toolCacheMethod.getAccelerator(id);


        switch (upgrade) {
            case 1:
                toolCacheMethod.setBotany(player.getName(), currentBotany + 1);
                player.sendTitle(
                        "§a§lBOTÂNICO!",
                        "§aO encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                break;
            case 2:
                if (currentAgility >= 2) {
                    toolCacheMethod.setExplosion(id, currentExplosion + 1);
                    player.sendTitle(
                            "§4§lEXPLOSÃO!",
                            "§4O encantamento foi aprimorado.",
                            10,
                            70,
                            20
                    );

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                } else {
                    toolCacheMethod.setAgility(player.getName(), currentAgility + 1);
                    player.sendTitle(
                            "§6§lAGILIDADE!",
                            "§6O encantamento foi aprimorado.",
                            10,
                            70,
                            20
                    );

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                }
                break;
            case 3:
                toolCacheMethod.setExplosion(player.getName(), currentExplosion + 1);
                player.sendTitle(
                        "§4§lEXPLOSÃO!",
                        "§4O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                break;
        }

    }
}

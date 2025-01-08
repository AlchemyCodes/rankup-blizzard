package blizzard.development.plantations.utils;

import org.bukkit.entity.Player;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class MessageUtils {

    public static void insufficientSeeds(Player player, int subtraction) {
        player.closeInventory();
        player.sendMessage("");
        player.sendMessage(" §c§lEI! §cVocê não possui sementes para evoluir.");
        player.sendMessage(" §cFaltam §l" + formatNumber(subtraction) + "§c sementes para executar a ação.");
        player.sendMessage("");
    }
}

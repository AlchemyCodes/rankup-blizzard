package blizzard.development.core.listener.generator;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.inventories.GeneratorInventory;
import blizzard.development.core.managers.GeneratorManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class GeneratorInteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;
        if (!CoreAPI.getInstance().isIsBlizzard()) {
            player.sendMessage("§b§lOPS! §fVocê não precisa ligar o gerador agora!");
            return;
        }

        GeneratorManager generatorManager = GeneratorManager.getInstance();

        if (generatorManager.hasGenerator(player)) {
            player.sendMessage("§b§lEBA! §fSeu gerador já está ativo!");
            return;
        }

        if (!generatorManager.isGenerator(
                clickedBlock.getWorld().getName(),
                clickedBlock.getX(),
                clickedBlock.getY(),
                clickedBlock.getZ(),
                5)) return;

        GeneratorInventory.open(player);
    }
}
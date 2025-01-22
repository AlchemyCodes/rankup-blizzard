package blizzard.development.farm.commands.storage;

import blizzard.development.farm.inventories.StorageInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import com.sk89q.minecraft.util.commands.CommandAlias;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("armazem|storage")
public class StorageCommand extends BaseCommand {

    @Default
    public void onStorageCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        StorageInventory storageInventory = new StorageInventory();
        storageInventory.open(player);
    }
}

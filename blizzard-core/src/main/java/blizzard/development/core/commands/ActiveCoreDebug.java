package blizzard.development.core.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("coredebug")
public class ActiveCoreDebug extends BaseCommand {

    public static HashMap<Player, Boolean> debugHashMap = new HashMap<>();

    @Default
    public void onCommand(Player player) {
        if (debugHashMap.getOrDefault(player, true)) {
            debugHashMap.put(player, false);
            return;
        }

        debugHashMap.put(player, true);
    }
}

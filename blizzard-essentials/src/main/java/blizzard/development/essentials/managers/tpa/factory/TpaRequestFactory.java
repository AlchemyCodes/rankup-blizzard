package blizzard.development.essentials.managers.tpa.factory;

import org.bukkit.entity.Player;

public interface TpaRequestFactory {

    void sendTpaRequest(Player player, Player target);
}

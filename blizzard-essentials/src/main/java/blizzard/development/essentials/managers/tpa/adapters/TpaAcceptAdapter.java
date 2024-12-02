package blizzard.development.essentials.managers.tpa.adapters;

import blizzard.development.essentials.managers.tpa.TpaManager;
import blizzard.development.essentials.managers.tpa.factory.TpaAcceptFactory;
import org.bukkit.entity.Player;

public class TpaAcceptAdapter implements TpaAcceptFactory {

    private static TpaAcceptAdapter instance;

    @Override
    public void tpaAccept(Player requester, Player target) {
        TpaManager tpaManager = TpaManager.getInstance();

        if (!tpaManager.contains(requester) || tpaManager.get(requester) != target) {
            requester.sendActionBar("§c§lEI! §cVocê não tem um pedido de teletransporte pendente para " + target.getName() + "!");
            return;
        }

        requester.sendActionBar("§a§lEI! §aSeu pedido de teletransporte para " + target.getName() + " foi aceito!");
        target.sendActionBar("§a§lEI! §aVocê aceitou o pedido de teletransporte de " + requester.getName() + "!");

        requester.teleport(target);

        tpaManager.remove(requester);
    }

    public static TpaAcceptAdapter getInstance() {
        if (instance == null) {
            instance = new TpaAcceptAdapter();
        }
        return instance;
    }
}
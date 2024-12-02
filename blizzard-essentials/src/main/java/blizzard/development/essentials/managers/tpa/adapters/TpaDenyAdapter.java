package blizzard.development.essentials.managers.tpa.adapters;

import blizzard.development.essentials.managers.tpa.TpaManager;
import blizzard.development.essentials.managers.tpa.factory.TpaDenyFactory;
import org.bukkit.entity.Player;

public class TpaDenyAdapter implements TpaDenyFactory {

    private static TpaDenyAdapter instance;

    @Override
    public void tpaDeny(Player requester, Player target) {
        TpaManager tpaManager = TpaManager.getInstance();

        if (!tpaManager.contains(requester) || tpaManager.get(requester) != target) {
            requester.sendActionBar("§c§lEI! §cVocê não tem um pedido de teletransporte pendente para " + target.getName() + "!");
            return;
        }

        requester.sendActionBar("§c§lEI! §cSeu pedido de teletransporte para " + target.getName() + " foi negado!");
        target.sendActionBar("§c§lEI! §cVocê negou o pedido de teletransporte de " + requester.getName() + "!");

        tpaManager.remove(requester);
    }

    public static TpaDenyAdapter getInstance() {
        if (instance == null) {
            instance = new TpaDenyAdapter();
        }
        return instance;
    }
}

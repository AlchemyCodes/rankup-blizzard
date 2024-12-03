package blizzard.development.essentials.managers.tpa.adapters;

import blizzard.development.essentials.managers.tpa.TpaManager;
import blizzard.development.essentials.managers.tpa.factory.TpaDenyFactory;
import org.bukkit.entity.Player;

public class TpaDenyAdapter implements TpaDenyFactory {

    private static TpaDenyAdapter instance;

    @Override
    public void tpaDeny(Player requester, Player target) {
        TpaManager tpaManager = TpaManager.getInstance();

        if (!tpaManager.contains(target)) {
            requester.sendActionBar("§c§lEI! §cVocê não tem um pedido de teletransporte pendente de " + target.getName() + "!");
            return;
        }

        target.sendActionBar("§c§lEI! §cSeu pedido de teletransporte para " + requester.getName() + " foi negado!");
        requester.sendActionBar("§c§lEI! §cO jogador " + target.getName() + " teve seu pedido de teletransporte negado!");

        tpaManager.remove(target);
    }


    public static TpaDenyAdapter getInstance() {
        if (instance == null) {
            instance = new TpaDenyAdapter();
        }
        return instance;
    }
}
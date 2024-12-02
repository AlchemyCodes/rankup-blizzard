package blizzard.development.essentials.managers.tpa.adapters;

import blizzard.development.essentials.managers.tpa.TpaManager;
import blizzard.development.essentials.managers.tpa.factory.TpaRequestFactory;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TpaRequestAdapter implements TpaRequestFactory {

    private static TpaRequestAdapter instance;

    @Override
    public void sendTpaRequest(Player player, Player target) {
        TpaManager tpaManager = TpaManager.getInstance();

        if (tpaManager.contains(player)) {
            player.sendActionBar("§c§lEI! §cVocê já tem um pedido de §lTPA§c para o jogador " + target.getName() + "!");
        } else {
            player.sendMessage("");
            player.sendMessage(" §d§lYAY! §dVocê enviou uma solicitação de");
            player.sendMessage(" §dteleporte para o jogador " + target.getName());
            player.sendMessage("");

            TextComponent message1 = new TextComponent(" §a§lEI! §aVocê recebeu uma solicitação de teleporte do jogador " + player.getName() + "\n");
            TextComponent message2 = new TextComponent("§7Clique ");

            TextComponent accept = accept(player, target);
            TextComponent message3 = new TextComponent(" §7para aceitar, ou clique ");
            TextComponent deny = deny(player);
            TextComponent message4 = new TextComponent(" §7para negar.");

            message1.addExtra(message2);
            message1.addExtra(accept);
            message1.addExtra(message3);
            message1.addExtra(deny);
            message1.addExtra(message4);

            target.spigot().sendMessage(message1);
            target.sendMessage("");

            tpaManager.add(player, target);
        }
    }

    private TextComponent accept(Player requester, Player target) {
        TextComponent acceptHere = new TextComponent("§a§lAQUI!");
        acceptHere.setClickEvent(new ClickEvent(
            ClickEvent.Action.RUN_COMMAND,
            "/tpaaccept " + requester.getName()
        ));
        acceptHere.setHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder("§aAceitar pedido de teletransporte.").create()
        ));
        return acceptHere;
    }

    private TextComponent deny(Player requester) {
        TextComponent denyHere = new TextComponent("§c§lAQUI!");
        denyHere.setClickEvent(new ClickEvent(
            ClickEvent.Action.RUN_COMMAND,
            "/tpadeny " + requester.getName()
        ));
        denyHere.setHoverEvent(new HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder("§cNegar pedido de teletransporte.").create()
        ));
        return denyHere;
    }

    public static TpaRequestAdapter getInstance() {
        if (instance == null) {
            instance = new TpaRequestAdapter();
        }
        return instance;
    }
}
package blizzard.development.monsters.inventories.main;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.inventories.main.items.MonstersItems;
import blizzard.development.monsters.inventories.tools.RadarInventory;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.packets.MonstersPacketsHandler;
import blizzard.development.monsters.monsters.handlers.tools.MonstersToolHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MonstersInventory {
    private static MonstersInventory instance;

    private final MonstersItems items = MonstersItems.getInstance();
    private final MonstersWorldHandler handler = MonstersWorldHandler.getInstance();

    public void open(Player player) {

        ChestGui inventory = new ChestGui(3, "§8Monstros");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem manageItem = new GuiItem(items.manage(), event -> {
            event.setCancelled(true);
        });

        GuiItem rewardsItem = new GuiItem(items.rewards(), event -> {
            event.setCancelled(true);
        });

        GuiItem goItem = new GuiItem(items.go(
                handler.containsPlayer(player)
        ), event -> {
            sendToWorld(player);
            player.getInventory().close();
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            event.setCancelled(true);
        });

        GuiItem swordItem = new GuiItem(items.sword(), event -> {
            giveSword(player);
            player.getInventory().close();
            event.setCancelled(true);
        });

        pane.addItem(manageItem, Slot.fromIndex(10));
        pane.addItem(rewardsItem, Slot.fromIndex(11));
        pane.addItem(goItem, Slot.fromIndex(13));
        pane.addItem(rankingItem, Slot.fromIndex(15));
        pane.addItem(swordItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void sendToWorld(Player player) {
        LocationUtils utils = LocationUtils.getInstance();

        if (handler.containsPlayer(player)) {
            Location exit = utils.getLocation(Locations.EXIT.getName());

            if (exit == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de saída não está configurado."));
                return;
            }

            handler.removePlayer(player);
            MonstersToolHandler.getInstance().removeRadar(player);

            player.teleport(exit);
            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê saiu do mundo de monstros."));
        } else {
            Location entry = utils.getLocation(Locations.ENTRY.getName());

            if (entry == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de entrada não está configurado."));
                return;
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                return;
            }

            handler.addPlayer(player);

            player.teleport(entry);
            MonstersToolHandler.getInstance().giveRadar(player);

            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê entrou no mundo de monstros."));
        }
    }

    private void giveSword(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
            return;
        }

        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê recebeu uma aniquiladora."));
        MonstersToolHandler.getInstance().giveSword(player, 2, 1);
    }

    public static MonstersInventory getInstance() {
        if (instance == null) instance = new MonstersInventory();
        return instance;
    }
}

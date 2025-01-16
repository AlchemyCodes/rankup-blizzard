package blizzard.development.monsters.inventories.main;

import blizzard.development.monsters.inventories.cage.CageInventory;
import blizzard.development.monsters.inventories.main.items.MonstersItems;
import blizzard.development.monsters.inventories.rewards.RewardsInventory;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.managers.tools.MonstersToolManager;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
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
    private final MonstersWorldManager worldManager = MonstersWorldManager.getInstance();

    public void open(Player player) {

        ChestGui inventory = new ChestGui(3, "§8Monstros");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem cageItem = new GuiItem(items.cage(), event -> {
            if (MonstersWorldManager.getInstance().containsPlayer(player)) {
                player.sendActionBar("§c§lEI! §cVocê só pode utilizar isso estando fora do mundo de monstros. §7(/monstros sair)");
                event.setCancelled(true);
                return;
            }
            CageInventory.open(player, 1);
            event.setCancelled(true);
        });

        GuiItem rewardsItem = new GuiItem(items.rewards(), event -> {
            RewardsInventory.open(player, 1);
            event.setCancelled(true);
        });

        GuiItem goItem = new GuiItem(items.go(
                worldManager.containsPlayer(player)
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

        pane.addItem(cageItem, Slot.fromIndex(10));
        pane.addItem(rewardsItem, Slot.fromIndex(11));
        pane.addItem(goItem, Slot.fromIndex(13));
        pane.addItem(rankingItem, Slot.fromIndex(15));
        pane.addItem(swordItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void sendToWorld(Player player) {
        LocationUtils utils = LocationUtils.getInstance();

        if (worldManager.containsPlayer(player)) {
            Location exit = utils.getLocation(Locations.EXIT.getName());

            if (exit == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO local de saída não está configurado."));
                return;
            }

            worldManager.removePlayer(player);
            MonstersToolManager.getInstance().removeRadar(player);

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

            worldManager.addPlayer(player);

            player.teleport(entry);
            MonstersToolManager.getInstance().giveRadar(player);

            player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê entrou no mundo de monstros."));
        }
    }

    private void giveSword(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
            return;
        }

        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê recebeu uma aniquiladora."));
        MonstersToolManager.getInstance().giveSword(player, 2, 1);
    }

    public static MonstersInventory getInstance() {
        if (instance == null) instance = new MonstersInventory();
        return instance;
    }
}

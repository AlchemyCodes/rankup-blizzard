package blizzard.development.monsters.inventories.cage;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.cache.methods.MonstersCacheMethods;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.inventories.cage.items.CageItems;
import blizzard.development.monsters.monsters.handlers.eggs.MonstersEggHandler;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CageInventory {
    private static CageInventory instance;

    private final CageItems items = CageItems.getInstance();

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(5, "§8Gaiola de monstros (Página " + page + " )");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<MonstersData> monsters = MonstersCacheManager.getInstance().monstersCache.values().stream().toList();

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, monsters.size());

        for (int i = 0; i < slots.length; i++) {

            int monsterIndex = startIndex + i;

            if (monsterIndex < endIndex) {
                String monsterType = monsters.get(monsterIndex).getType();

                GuiItem monsterItem = new GuiItem(items.monster(monsterType), event -> {
                    catchMonster(player, monsters.get(monsterIndex));
                    player.getOpenInventory().close();
                    event.setCancelled(true);
                });

                if (monsters.get(monsterIndex).getOwner().equals(player.getName())) {
                    pane.addItem(monsterItem, Slot.fromIndex(Integer.parseInt(slots[i])));
                }
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        if (monsters.size() > endIndex) {
            GuiItem nextItem = new GuiItem(items.next(), event -> {
                open(player, page + 1);
                event.setCancelled(true);
            });
            pane.addItem(nextItem, Slot.fromIndex(35));
        }

        if (page > 1) {
            GuiItem previousItem = new GuiItem(items.previous(), event -> {
                open(player, page - 1);
                event.setCancelled(true);
            });
            pane.addItem(previousItem, Slot.fromIndex(27));
        }

        GuiItem catchAllItem = new GuiItem(items.catchAll(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(catchAllItem, Slot.fromIndex(40));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void catchMonster(Player player, MonstersData monstersData) {
        if (MonstersWorldHandler.getInstance().containsPlayer(player)) {
            player.sendActionBar("§c§lEI! §cVocê só pode utilizar isso estando fora do mundo de monstros. §7(/monstros sair)");
            return;
        }

        MonstersHandler monstersHandler = MonstersHandler.getInstance();
        MonstersEggHandler eggHandler = MonstersEggHandler.getInstance();

        ConfigurationSection monstersSection = monstersHandler.getSection();
        Set<String> monsters = monstersHandler.getAll();

        if (monstersSection == null || monsters == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao encontrar os monstros na configuração."));
            return;
        }

        if (monsters.contains(monstersData.getType())) {
            if (player.getInventory().firstEmpty() == -1) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                return;
            }

            String displayName = MonstersHandler.getInstance().getDisplayName(monstersData.getType());

            Arrays.asList(
                    "",
                    " §3§lMonstros! §7Você capturou um monstro.",
                    " §7O monstro " + displayName,
                    " §7foi capturado com sucesso.",
                    ""
            ).forEach(player::sendMessage);

            MonstersCacheManager.getInstance().removeMonsterData(monstersData.getUuid());

            try {
                new MonstersDAO().deleteMonsterData(monstersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            eggHandler.giveEgg(player, monstersData.getType(), 1);
        } else {
            Arrays.asList(
                    "",
                    " §c§lEI §cO monstro §7" + monstersData.getType() + "§c não existe.",
                    ""
            ).forEach(player::sendMessage);
        }
    }

    public static CageInventory getInstance() {
        if (instance == null) instance = new CageInventory();
        return instance;
    }
}

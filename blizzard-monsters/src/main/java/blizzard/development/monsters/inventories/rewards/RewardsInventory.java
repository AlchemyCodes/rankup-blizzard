package blizzard.development.monsters.inventories.rewards;

import blizzard.development.monsters.inventories.rewards.items.RewardsItems;
import blizzard.development.monsters.monsters.managers.monsters.cage.MonstersCageManager;
import blizzard.development.monsters.monsters.managers.monsters.rewards.MonstersRewardManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

import java.util.List;

public class RewardsInventory {
    private static RewardsInventory instance;

    private final RewardsItems items = RewardsItems.getInstance();

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(5, "§8Recompensas (Página " + page + ")");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> rewards = MonstersRewardManager.getInstance().getAll().stream().toList();

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, rewards.size());

        MonstersRewardManager manager = MonstersRewardManager.getInstance();

        for (int i = 0; i < slots.length; i++) {

            int rewardIndex = startIndex + i;

            if (rewardIndex < endIndex) {
                String rewardType = rewards.get(rewardIndex);

                GuiItem rewardItem = new GuiItem(items.reward(player, rewardType), event -> {
                    if (event.isLeftClick()) {
                        manager.collectReward(player, rewardType, false);
                    } else if (event.isRightClick()) {
                        manager.collectReward(player, rewardType, true);
                    }
                    player.getOpenInventory().close();
                    event.setCancelled(true);
                });


                pane.addItem(rewardItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        if (rewards.size() > endIndex) {
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

        GuiItem collectAllItem = new GuiItem(items.collectAll(), event -> {
            if (!player.hasPermission("blizzard.monsters.vip")) {
                player.sendActionBar("§c§lEI! §cVocê não possui permissão para utilizar isso.");
                return;
            }

            manager.collectAllRewards(player);
            player.getInventory().close();
            event.setCancelled(true);
        });

        pane.addItem(collectAllItem, Slot.fromIndex(40));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static RewardsInventory getInstance() {
        if (instance == null) instance = new RewardsInventory();
        return instance;
    }
}

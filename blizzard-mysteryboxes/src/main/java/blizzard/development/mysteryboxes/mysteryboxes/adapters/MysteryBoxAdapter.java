package blizzard.development.mysteryboxes.mysteryboxes.adapters;

import blizzard.development.mysteryboxes.mysteryboxes.factory.MysteryBoxFactory;
import blizzard.development.mysteryboxes.mysteryboxes.item.MysteryBoxBuildItem;
import org.bukkit.entity.Player;

public class MysteryBoxAdapter implements MysteryBoxFactory {

    private static final MysteryBoxAdapter instance = new MysteryBoxAdapter();
    public static MysteryBoxAdapter getInstance() {
        return instance;
    }

    @Override
    public void giveRareMysteryBox(Player player, int amount) {
        player.getInventory().addItem(
            MysteryBoxBuildItem.getInstance()
            .rareMysteryBox(amount));
    }

    @Override
    public void giveLegendaryMysteryBox(Player player, int amount) {
        player.getInventory().addItem(
            MysteryBoxBuildItem.getInstance()
                .legendaryMysteryBox(amount));
    }

    @Override
    public void giveBlizzardMysteryBox(Player player, int amount) {
        player.getInventory().addItem(
            MysteryBoxBuildItem.getInstance()
                .blizzardMysteryBox(amount));
    }
}

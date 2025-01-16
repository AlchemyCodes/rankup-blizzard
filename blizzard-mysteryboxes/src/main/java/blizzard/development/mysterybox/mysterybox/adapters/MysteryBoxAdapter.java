package blizzard.development.mysterybox.mysterybox.adapters;

import blizzard.development.mysterybox.mysterybox.factory.MysteryBoxFactory;
import blizzard.development.mysterybox.mysterybox.item.MysteryBoxBuildItem;
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

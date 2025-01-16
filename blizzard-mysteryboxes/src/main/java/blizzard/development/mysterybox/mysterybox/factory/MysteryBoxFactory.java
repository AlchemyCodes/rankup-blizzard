package blizzard.development.mysterybox.mysterybox.factory;

import org.bukkit.entity.Player;

public interface MysteryBoxFactory {

    void giveRareMysteryBox(Player player, int amount);
    void giveLegendaryMysteryBox(Player player, int amount);
    void giveBlizzardMysteryBox(Player player, int amount);
}

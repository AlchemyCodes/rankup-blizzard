package blizzard.development.crates.crates.factory;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface CrateFactory {

    void commonCrate(Player player, ArmorStand armorStand);
    void rareCrate(Player player, ArmorStand armorStand);
    void mysticCrate(Player player, ArmorStand armorStand);
    void legendaryCrate(Player player, ArmorStand armorStand);
    void blizzardCrate(Player player, ArmorStand armorStand);
}

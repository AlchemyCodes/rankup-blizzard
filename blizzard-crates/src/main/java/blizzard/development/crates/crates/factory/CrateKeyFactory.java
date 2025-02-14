package blizzard.development.crates.crates.factory;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface CrateKeyFactory {

    void commonKey(Player player, ArmorStand armorStand);
    void rareKey(Player player, ArmorStand armorStand);
    void mysticKey(Player player, ArmorStand armorStand);
    void legendaryKey(Player player, ArmorStand armorStand);
    void blizzardKey(Player player, ArmorStand armorStand);
}

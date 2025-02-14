package blizzard.development.crates.crates.adapters;

import blizzard.development.crates.crates.factory.CrateFactory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class CrateAdapter implements CrateFactory {

    private final CrateKeyAdapter crateKeyAdapter = CrateKeyAdapter.getInstance();

    @Override
    public void commonCrate(Player player, ArmorStand armorStand) {
    }

    @Override
    public void rareCrate(Player player, ArmorStand armorStand) {

    }

    @Override
    public void mysticCrate(Player player, ArmorStand armorStand) {

    }

    @Override
    public void legendaryCrate(Player player, ArmorStand armorStand) {

    }

    @Override
    public void blizzardCrate(Player player, ArmorStand armorStand) {

    }
}

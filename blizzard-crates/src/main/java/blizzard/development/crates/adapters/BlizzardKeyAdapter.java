package blizzard.development.crates.adapters;

import blizzard.development.crates.factory.KeyHandlerInterface;
import blizzard.development.crates.managers.rewards.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlizzardKeyAdapter implements KeyHandlerInterface {
    @Override
    public void handle(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (armorStand.hasMetadata("blizzard")) {
            item.setAmount(item.getAmount() - 1);

            Location location = armorStand.getLocation();
            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            firework.eject();

            player.sendActionBar("§b§lWOW! §bVocê abriu uma caixa Comum!");
            RewardManager rewardManager = new RewardManager();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        }
    }
}

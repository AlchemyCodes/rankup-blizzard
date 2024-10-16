package blizzard.development.crates.adapters;

import blizzard.development.crates.factory.KeyHandlerInterface;
import blizzard.development.crates.managers.rewards.RewardManager;
import blizzard.development.crates.utils.item.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommonKeyAdapter implements KeyHandlerInterface {
    @Override
    public void handle(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!NBTUtils.hasTag(item, "chave.comum")) {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Comum` §cpara abrir a caixa!");
            return;
        }

        if (armorStand.hasMetadata("comum")) {
            item.setAmount(item.getAmount() - 1);

            Location location = armorStand.getLocation();
            Firework firework = (Firework) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.FIREWORK);
            firework.eject();

            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);

            player.sendActionBar("§b§lWOW! §bVocê abriu uma caixa Comum!");
            RewardManager rewardManager = new RewardManager();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        }
    }
}

package blizzard.development.crates.crates.adapters;

import blizzard.development.crates.Main;
import blizzard.development.crates.builder.ItemBuilder;
import blizzard.development.crates.crates.factory.CrateKeyFactory;
import blizzard.development.crates.managers.rewards.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateKeyAdapter implements CrateKeyFactory {

    private static final CrateKeyAdapter instance = new CrateKeyAdapter();
    public static CrateKeyAdapter getInstance() {
        return instance;
    }

    @Override
    public void commonKey(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "chave.comum")) {

            Location location = armorStand.getLocation();
            firework(location);
            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);
            player.sendActionBar("§8§lYeah! §8Você abriu uma caixa Comum.");

            RewardManager rewardManager = new RewardManager();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        } else {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Comum` §cpara abrir a caixa!");
        }
    }

    @Override
    public void rareKey(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "chave.rara")) {
            Location location = armorStand.getLocation();
            firework(location);
            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);
            player.sendActionBar("§a§lYeah! §aVocê abriu uma caixa Rara.");

            RewardManager rewardManager = new RewardManager();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        } else {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Rara` §cpara abrir a caixa!");
        }
    }

    @Override
    public void mysticKey(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "chave.mistica")) {
            Location location = armorStand.getLocation();
            firework(location);
            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);
            player.sendActionBar("§d§lYeah! §dVocê abriu uma caixa Mística.");

            RewardManager rewardManager = new RewardManager();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        } else {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Mística` §cpara abrir a caixa!");
        }
    }

    @Override
    public void legendaryKey(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "chave.lendaria")) {
            Location location = armorStand.getLocation();
            firework(location);
            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);
            player.sendActionBar("§6§lYeah! §6Você abriu uma caixa Lendaria.");

            RewardManager rewardManager = new RewardManager();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        } else {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Lendaria` §cpara abrir a caixa!");
        }
    }

    @Override
    public void blizzardKey(Player player, ArmorStand armorStand) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "chave.blizzard")) {
            Location location = armorStand.getLocation();
            firework(location);
            player.spawnParticle(Particle.FIREWORKS_SPARK, location.add(0, -1, 0), 2);
            player.sendActionBar("§b§lYeah! §bVocê abriu uma caixa Blizzard.");

            RewardManager rewardManager = new RewardManager();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewardManager.generateReward(player));
        } else {
            player.sendActionBar("§c§lEI §cVocê precisa de uma chave §7`Blizzard` §cpara abrir a caixa!");
        }
    }

    private void firework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.FIREWORK);
        firework.eject();
    }
}
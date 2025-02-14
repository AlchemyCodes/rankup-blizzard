package blizzard.development.crates.listeners.crates;

import blizzard.development.crates.Main;
import blizzard.development.crates.builder.ItemBuilder;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.crates.managers.CrateManager.createCrate;

public class CratePlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        double x = block.getX() + 0.5;
        double y = block.getY() - 0.3;
        double z = block.getZ() + 0.5;

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "comum")) {
            createCrate(
                    player,
                    new Location(block.getWorld(), x, y, z),
                    "§8§lCaixa Comum!\n§7Clique para abrir.",
                    Particle.ELECTRIC_SPARK,
                    1,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODAxMzk4YTY5MWFiYjg1NWNjZTFjY2I2YmQzYmFkZTUwYmEyNWJlMDEyY2FiZWYzMWZiZGYwM2FlNDliYzg4NSJ9fX0=",
                    "§8§lCaixa Comum!\n§7Clique para abrir.",
                    "comum"
            );
            event.setCancelled(true);
        }

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "rara")) {
            createCrate(
                    player,
                    new Location(block.getWorld(), x, y, z),
                    "§a§lCaixa Rara!\n§7Clique para abrir.",
                    Particle.VILLAGER_HAPPY,
                    1,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwODRlODlhNzBkNzA5OGI2OTQ4MzRlNTM0OTY3NDE1NjcwYjgwODM3YWZiOWJiOWY0YTI2ZmZiNzk3Zjg5MSJ9fX0=",
                    "§a§lCaixa Rara!\n§7Clique para abrir.",
                    "rara"
            );
            event.setCancelled(true);
        }

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "mistica")) {
            createCrate(
                    player,
                    new Location(block.getWorld(), x, y, z),
                    "§5§lCaixa Mística!\n§7Clique para abrir.",
                    Particle.SPELL_WITCH,
                    1,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM0NmUyZTkzNmRjY2IwZWRjNzAzNmJiNWRjYjEyNjJhYzAwMDYzODUxNWVmZTM0ZmZiOTdkYjY4NzY0NjIyMSJ9fX0=",
                    "§5§lCaixa Mística!\n§7Clique para abrir.",
                    "mitica"
            );
            event.setCancelled(true);
        }

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "lendaria")) {
            createCrate(
                    player,
                    new Location(block.getWorld(), x, y, z),
                    "§6§lCaixa Lendária!\n§7Clique para abrir.",
                    Particle.LAVA,
                    0,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBmYzk4MTk1MDBmNmQxMWEwM2IzMjJhMmZmM2I2ZjU1NDg4Y2MyMzI4ODRhZDZmNDczNjZmZTU4MGEzYmE0ZiJ9fX0=",
                    "§6§lCaixa Lendária!\n§7Clique para abrir.",
                    "lendaria"
            );
            event.setCancelled(true);
        }

        if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "blizzard")) {
            createCrate(
                    player,
                    new Location(block.getWorld(), x, y, z),
                    "§b§lCaixa Blizzard!\n§7Clique para abrir.",
                    Particle.SNOW_SHOVEL,
                    3,
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMzMzZkNzQxNjI1ZGMwYWY0NjAyNGE4YzY1NDMwN2U1Njk1ZWZkMzc5YjE1OTY3OGFlNjVjZmEwNGEyMzkxNCJ9fX0=",
                    "§b§lCaixa Blizzard!\n§7Clique para abrir.",
                    "blizzard"
            );
            event.setCancelled(true);
        }

    }
}

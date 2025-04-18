package blizzard.development.monsters.listeners.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.database.cache.methods.PlayersCacheMethods;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.packets.MonstersPacketsManager;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MonstersEggListener implements Listener {

    private final Plugin plugin = PluginImpl.getInstance().plugin;

    @EventHandler
    public void onEggInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CooldownUtils cooldown = CooldownUtils.getInstance();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            MonstersGeneralManager generalManager = MonstersGeneralManager.getInstance();
            MonstersPacketsManager packetsManager = MonstersPacketsManager.getInstance();

            ItemStack item = player.getInventory().getItemInMainHand();
            boolean isMonsterEgg = ItemBuilder.hasPersistentData(plugin, item, "blizzard.monsters.monster");
            String eggType = ItemBuilder.getPersistentData(plugin, item, "blizzard.monsters.monster");

            if (isMonsterEgg) {
                if (!MonstersWorldManager.getInstance().containsPlayer(player)) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê só pode chocar este ovo no mundo de monstros."));
                    event.setCancelled(true);
                    return;
                }

                ConfigurationSection monstersSection = generalManager.getSection();
                Set<String> monsters = generalManager.getAll();

                if (monstersSection == null || monsters == null) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao encontrar os monstros na configuração."));
                    return;
                }

                if (!monsters.contains(eggType)) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cEste monstro não existe."));
                    return;
                }

                if (getMonstersAmount(player) + 1 > PlayersCacheMethods.getInstance().getMonstersLimit(player)) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê atingiu a quantia máxima de monstros simultâneos."));
                    return;
                }

                String cooldownName = "blizzard.monsters.egg-cooldown";

                if (cooldown.isInCountdown(player, cooldownName) && !player.hasPermission("blizzard.monsters.admin")) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de chocar um ovo."));
                    return;
                }

                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock == null) return;

                Location spawnLocation = LocationUtils.getInstance().calculateSpawnLocation(clickedBlock, event.getBlockFace());

                Vector direction = player.getLocation().toVector().subtract(spawnLocation.toVector());
                spawnLocation.setDirection(direction);

                String type = generalManager.getType(eggType);
                String displayName = generalManager.getDisplayName(eggType);
                Integer life = generalManager.getLife(eggType);
                String value = generalManager.getSkinValue(eggType);
                String signature = generalManager.getSkinSignature(eggType);

                packetsManager.spawnMonster(
                        player,
                        type,
                        spawnLocation,
                        displayName,
                        life,
                        value,
                        signature
                );

                List<String> messages = Arrays.asList(
                        "",
                        "  §3§lMonstros! §f✧ §7Você chocou um monstro.",
                        "  §7O monstro " + displayName + "§7 está a solta.",
                        "  §8[Use o radar e descubra sua localização]",
                        ""
                );

                messages.forEach(player::sendMessage);
                manageStack(player);

                cooldown.createCountdown(player, cooldownName, 3, TimeUnit.SECONDS);

                event.setCancelled(true);
            }
        }
    }

    private void manageStack(Player player) {
        ItemStack currentItem = player.getInventory().getItemInMainHand();
        if (currentItem.getAmount() > 1) {
            currentItem.setAmount(currentItem.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }

    private int getMonstersAmount(Player player) {
        return MonstersGeneralManager.getInstance().getMonsters(player).size();
    }
}

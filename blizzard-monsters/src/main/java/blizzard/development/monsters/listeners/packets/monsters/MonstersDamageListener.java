package blizzard.development.monsters.listeners.packets.monsters;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.cache.methods.ToolsCacheMethods;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.monsters.attack.MonstersDamageManager;
import blizzard.development.monsters.monsters.managers.monsters.attack.MonstersDeathManager;
import blizzard.development.monsters.utils.CooldownUtils;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class MonstersDamageListener {
    private static MonstersDamageListener instance;

    public MonstersDamageListener(Plugin plugin) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL,
                PacketType.Play.Client.USE_ENTITY) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();
                if (action != EnumWrappers.EntityUseAction.ATTACK) {
                    return;
                }

                String entityId = packet.getIntegers().read(0).toString();

                MonstersGeneralManager monstersManager = MonstersGeneralManager.getInstance();

                if (monstersManager.isMonster(player, entityId)) {

                    MonstersData data = null;
                    for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                        if (monstersData.getId().equals(entityId)) {
                            data = monstersData;
                            break;
                        }
                    }

                    if (data != null) {
                        Location monsterLocation = LocationUtils.getInstance().deserializeLocation(data.getLocation());

                        ItemStack item = player.getInventory().getItemInMainHand();

                        if (!ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin,
                                item, "blizzard.monsters.sword")) {
                            player.sendActionBar("§c§lEI! §cVocê precisa de uma §7Aniquiladora §cpara matar esse monstro.");
                            return;
                        }

                        String monster = data.getType();
                        String uuid = data.getUuid();
                        String displayName = monstersManager.getDisplayName(monster);

                        String swordId = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.monsters.sword");
                        int damage = ToolsCacheMethods.getInstance().getDamage(swordId);

                        int life = data.getLife();
                        int finalLife = life - damage;

                        if (finalLife <= 0) {
                            MonstersDeathManager.getInstance().killMonster(
                                    player,
                                    data,
                                    displayName
                            );

                        }

                        MonstersDamageManager.getInstance().receiveDamage(
                                player,
                                monsterLocation,
                                monster,
                                uuid,
                                displayName,
                                finalLife,
                                damage
                        );
                    } else {
                        player.sendActionBar("§c§lEI! §cOcorreu um erro ao contrar esse monstro no banco de dados.");
                    }
                }
            }
        });
    }

    public static MonstersDamageListener getInstance(Plugin plugin) {
        if (instance == null) instance = new MonstersDamageListener(plugin);
        return instance;
    }
}
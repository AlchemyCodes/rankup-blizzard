package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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

                int entityId = packet.getIntegers().read(0);

                MonstersHandler monstersHandler = MonstersHandler.getInstance();

                if (monstersHandler.isMonster(player, String.valueOf(entityId))) {
                    player.sendMessage("§aVocê atacou um monstro seu!");
                }
            }
        });
    }

    public static MonstersDamageListener getInstance(Plugin plugin) {
        if (instance == null) instance = new MonstersDamageListener(plugin);
        return instance;
    }
}
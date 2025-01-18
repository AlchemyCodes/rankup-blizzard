package blizzard.development.mine.listeners.packets.npc;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.managers.mine.NPCManager;
import blizzard.development.mine.mine.events.npc.NPCInteractEvent;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NPCInteractPacketListener extends PacketAdapter {

    public NPCInteractPacketListener() {
        super(PacketAdapter.params(
                PluginImpl.getInstance().plugin,
                PacketType.Play.Client.USE_ENTITY
        ).optionAsync());
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();

        EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();
        if (action != EnumWrappers.EntityUseAction.INTERACT) {
            return;
        }

        Integer entityId = packet.getIntegers().read(0);

        if (NPCManager.getInstance().getNPCId(player).equals(entityId)) {
            if (PlayerCacheMethods.getInstance().isInMine(player)) {
                NPCInteractEvent npcEvent = new NPCInteractEvent(player, entityId);
                Bukkit.getPluginManager().callEvent(npcEvent);
            }
        }
    }
}
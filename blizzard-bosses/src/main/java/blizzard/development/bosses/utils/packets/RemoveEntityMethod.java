package blizzard.development.bosses.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.entity.Player;

public class RemoveEntityMethod {

    public static void removeEntity(Player player, Integer entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        final PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getIntLists().write(0, new IntArrayList(new int[]{entityId}));
        try {
            protocolManager.sendServerPacket(player, destroyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
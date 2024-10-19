package blizzard.development.essentials.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;


public class PacketSender {
    public static void sendPackets(Player player, long amount) {
        for (int i = 0; i < amount; i++) {

            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CHAT);

            WrappedChatComponent chatComponent = WrappedChatComponent.fromText("Packet " + (i + 1));

            packet.getChatComponents().write(0, chatComponent);
            packet.getChatTypes().write(0, EnumWrappers.ChatType.CHAT);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        }
    }
}

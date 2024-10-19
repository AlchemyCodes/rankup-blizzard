package blizzard.development.bosses.utils.packets.npcs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCInfo {
    private ProtocolManager protocolManager;
    private UUID uuid;

    public NPCInfo(
            ProtocolManager protocolManager,
            UUID uuid) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
    }
    public void playerInfoUpdate(Player player, String npcName, String textureValue, String textureSignature) {

        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        Set<EnumWrappers.PlayerInfoAction> playerInfoActionSet = new HashSet<>();
        WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(uuid, npcName);

        WrappedSignedProperty property = new WrappedSignedProperty(
                "textures",
                textureValue,
                textureSignature);

        wrappedGameProfile.getProperties().clear();
        wrappedGameProfile.getProperties()
                .put("textures", property);

        PlayerInfoData playerInfoData = new PlayerInfoData(
                wrappedGameProfile,
                0,
                EnumWrappers.NativeGameMode.CREATIVE,
                WrappedChatComponent.fromText("name"));

        List<PlayerInfoData> playerInfoDataList = Arrays.asList(playerInfoData);
        playerInfoActionSet.add(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        npc.getPlayerInfoActions()
                .write(0, playerInfoActionSet);
        npc.getPlayerInfoDataLists().write(1, playerInfoDataList);

        protocolManager.sendServerPacket(player, npc);
    }
}

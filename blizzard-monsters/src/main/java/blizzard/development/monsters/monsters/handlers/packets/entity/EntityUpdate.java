    package blizzard.development.monsters.monsters.handlers.packets.entity;

    import com.comphenix.protocol.PacketType;
    import com.comphenix.protocol.ProtocolManager;
    import com.comphenix.protocol.events.PacketContainer;
    import com.comphenix.protocol.wrappers.*;
    import org.bukkit.entity.Player;

    import java.util.*;

    public class EntityUpdate {
        private static EntityUpdate instance;

        public void playerInfoUpdate(Player player, UUID uuid, String value, String signature, ProtocolManager protocolManager) {
            PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
            Set<EnumWrappers.PlayerInfoAction> playerInfoActionSet = new HashSet<>();

            WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(uuid, null);

            WrappedSignedProperty property = new WrappedSignedProperty(
                    "textures", value, signature);

            wrappedGameProfile.getProperties().clear();
            wrappedGameProfile.getProperties()
                    .put("textures", property);

            PlayerInfoData playerInfoData = new PlayerInfoData(
                    wrappedGameProfile,
                    0,
                    EnumWrappers.NativeGameMode.CREATIVE,
                    WrappedChatComponent.fromText("name"));


            List<PlayerInfoData> playerInfoDataList = List.of(playerInfoData);

            playerInfoActionSet.add(EnumWrappers.PlayerInfoAction.ADD_PLAYER);

            npc.getPlayerInfoActions()
                    .write(0, playerInfoActionSet);

            npc.getPlayerInfoDataLists().write(1, playerInfoDataList);

            protocolManager.sendServerPacket(player, npc);
        }

        public static EntityUpdate getInstance() {
            if (instance == null) instance = new EntityUpdate();
            return instance;
        }
    }

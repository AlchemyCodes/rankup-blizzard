package blizzard.development.monsters.monsters.handlers.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.entity.Player;

import java.util.*;

public class EntityUpdate {
    private static EntityUpdate instance;

    public void playerInfoUpdate(Player player, UUID uuid, ProtocolManager protocolManager) {
        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        Set<EnumWrappers.PlayerInfoAction> playerInfoActionSet = new HashSet<>();

        WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(uuid, null);

        WrappedSignedProperty property = new WrappedSignedProperty(
                "textures",
                "ewogICJ0aW1lc3RhbXAiIDogMTY2MTAxOTUyMDY4NiwKICAicHJvZmlsZUlkIiA6ICJjOWRlZTM4MDUzYjg0YzI5YjZlZjA5YjJlMDM5OTc0ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTQVJfRGVjZW1iZXI1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzIwNTlhNWI0NGRhZDM1ZmM3NzAwYjBhNjM1OTk0MzljMTkzZTUzNmI4ODI5OTk4OWUzZTlkOTVjNmJiZTk5ZjQiCiAgICB9CiAgfQp9",
                "hgk9saDDmB9i4OtGrEScfrDQpufsWnNGVa2Fd3W1dfAr9lQrbtuJu1VX4L/yHYFPxDCIFJspC819dVKHP2YWTLUSvcCbJm/+8jRkBXc4nk8Brlt7otUQpNEB02kwgABFtVHGKM08JAwuVx+J05JvC2UeKYgCSKryZ4iPxFEohr/Fq+zF3RcepPvbvkQ43tOOA6ZRdyyyF+8KXvoTtGR0Gf4I8VEvPh4+Z+JIWqHSohLpDleIcVUs59JfsodyPBid5+vXZMAGx05TNd96XTwRsD26k17cWGGesS67C/dBmFVbm8RBPn/hZPFpHvctw85tYRpQjYFQ0iYhOgxpdonW+3/U59J6oIMGdS4JXUVCGV+VKIJ6CaMYoVyxkjkHwMjOh7Gmj4O3I3AQOM+vvk+DepaJoq3IJh7m7nxU07XRpWeBvc2m8BAah6/9P0Ex4t3TYiS+1l4BgIb1SikmOBNUIscODMJ/PiTYDT/g4lDxMZolmH+HeKcL1dPYmcPgCjPh5hkXmX7Lt6jTIC143szruSEabgVokSKgGGJXjVEoV4vnY04C0txV1WCLNpGrlMmw6WdVmPZRGd24Xb+plZBMmX/B+b6m1Awe+Rk5WqHSdVd6EZGm+I/esaUhgp8NY55HxuKMuErWznxyPvDE+1TSNoEyfWWLjAVc+aPNpqY6XH0="
        );

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

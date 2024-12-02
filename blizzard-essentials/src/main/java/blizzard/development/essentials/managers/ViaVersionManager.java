package blizzard.development.essentials.managers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.bukkit.entity.Player;

public class ViaVersionManager {

    public static boolean isBelowVersion(Player player, ProtocolVersion targetVersion) {
        if (player == null) {
            return false;
        }

        ViaAPI<?> api = Via.getAPI();
        int playerVersion = api.getPlayerVersion(player.getUniqueId());

        return playerVersion < targetVersion.getVersion();
    }

    public static boolean isExactVersion(Player player, ProtocolVersion targetVersion) {
        if (player == null) {
            return false;
        }

        try {
            ViaAPI<?> api = Via.getAPI();
            int playerVersion = api.getPlayerVersion(player.getUniqueId());

            return playerVersion < targetVersion.getVersion();
        } catch (Exception e) {
            System.err.println("Erro ao verificar versão do protocolo: " + e.getMessage());
            return false;
        }
    }

    public static boolean isAboveVersion(Player player, ProtocolVersion targetVersion) {
        if (player == null) {
            return false;
        }

        try {
            ViaAPI<?> api = Via.getAPI();
            int playerVersion = api.getPlayerVersion(player.getUniqueId());

            return playerVersion > targetVersion.getVersion();
        } catch (Exception e) {
            System.err.println("Erro ao verificar versão do protocolo: " + e.getMessage());
            return false;
        }
    }

    public static int getPlayerProtocolVersion(Player player) {
        if (player == null) {
            return -1;
        }

        try {
            ViaAPI<?> api = Via.getAPI();
            return api.getPlayerVersion(player.getUniqueId());
        } catch (Exception e) {
            System.err.println("Erro ao obter versão do protocolo: " + e.getMessage());
            return -1;
        }
    }
}
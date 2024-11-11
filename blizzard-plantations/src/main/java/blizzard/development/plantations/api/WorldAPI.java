package blizzard.development.plantations.api;

import blizzard.development.slimeworlds.api.SlimeWorldAPI;
import org.bukkit.entity.Player;

public class WorldAPI {

    public void createWorld(Player player) {
        SlimeWorldAPI slimeWorldAPI = SlimeWorldAPI.getInstance();

        try {
            slimeWorldAPI.createWorld(
                    player
            );
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void teleportToWorld(Player player) {
        SlimeWorldAPI slimeWorldAPI = SlimeWorldAPI.getInstance();

        try {
            slimeWorldAPI.teleportToWorld(
                    player
            );
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

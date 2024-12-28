package blizzard.development.plantations.api;

import org.bukkit.entity.Player;

public class CoreAPI {

    private static final CoreAPI instance = new CoreAPI();


    public String clothing(Player player) {
        blizzard.development.core.api.CoreAPI coreAPI = blizzard.development.core.api.CoreAPI.getInstance();

        return coreAPI.getPlayerClothing(player);
    }

    public double temperature(Player player) {
        blizzard.development.core.api.CoreAPI coreAPI = new blizzard.development.core.api.CoreAPI();

        return coreAPI.getTemperature(player);
    }


    public static CoreAPI getInstance() {
        return instance;
    }
}

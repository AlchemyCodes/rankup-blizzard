package blizzard.development.essentials.managers;

import blizzard.development.essentials.utils.PluginImpl;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AnnounceManager {

    private static final AnnounceManager instance = new AnnounceManager();

    YamlConfiguration config = PluginImpl.getInstance().Config.getConfig();
    Set<String> announces = config.getConfigurationSection("announces").getKeys(false);

    public static AnnounceManager getInstance() {
        return instance;
    }

    public void send(Player player) {
        if (!announces.isEmpty()) {

            String key = announces.stream()
                    .skip(ThreadLocalRandom.current().nextInt(announces.size()))
                    .findFirst()
                    .orElse(null);

            if (key != null) {
                List<String> messages = config.getStringList("announces." + key + ".message");
                String actionbar = config.getString("announces." + key + ".actionbar");

                if (!messages.isEmpty()) {
                    StringBuilder announcement = new StringBuilder();
                    for (String message : messages) {
                        announcement.append(message).append("\n");
                    }

                    player.sendMessage(announcement.toString());
                    player.sendActionBar(actionbar);
                }
            }
        }
    }
}

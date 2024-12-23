package blizzard.development.plantations.managers.upgrades.blizzard;

import blizzard.development.plantations.plantations.enums.BlizzardEnum;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;

public class BlizzardEffect {

    public HashMap<Player, BlizzardEnum> blizzard = new HashMap<>();
    private static final BlizzardEffect instance = new BlizzardEffect();

    public static BlizzardEffect getInstance() {
        return instance;
    }

    public void startBlizzardEffect(Player player) {
        if (blizzard.containsKey(player)) return;


        player.showTitle(
            Title.title(
                TextUtils.parse("<bold><#55FFFF>Ne<#72f7f7><#72f7f7>vas<#72f7f7><#72f7f7>ca!<#55FFFF></bold>"),
                TextUtils.parse("<#72f7f7>O encantamento foi ativado.<#72f7f7>"),
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
            )
        );


        player.setPlayerWeather(WeatherType.DOWNFALL);
        blizzard.put(player, BlizzardEnum.ACTIVE);
    }

    public void stopBlizzardEffect(Player player) {
        if (!blizzard.containsKey(player)) return;

        player.setPlayerWeather(WeatherType.CLEAR);
        blizzard.remove(player);
    }

}

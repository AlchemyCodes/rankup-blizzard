package blizzard.development.mine.expansions;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.enums.BlockEnum;
import blizzard.development.mine.utils.text.NumberUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "mine";
    }

    @Override
    public @NotNull String getAuthor() {
        return "AlchemyNetwork";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

        int blocks = cacheMethods.getBlocks(player);

        String area = BlockEnum.valueOf(
                cacheMethods.getAreaBlock(player)
        ).getType();

        return switch (identifier) {
            case "blocks" -> NumberUtils.getInstance().formatNumber(blocks);
            case "area" -> area;
            default -> identifier;
        };
    }
}

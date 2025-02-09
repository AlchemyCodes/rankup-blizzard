package blizzard.development.vips.commands.key;

import blizzard.development.vips.database.cache.KeyCacheManager;
import blizzard.development.vips.database.cache.methods.KeyCacheMethod;
import blizzard.development.vips.database.dao.KeyDAO;
import blizzard.development.vips.plugin.PluginImpl;
import blizzard.development.vips.utils.VipUtils;
import blizzard.development.vips.vips.adapters.VipsAdapter;
import blizzard.development.vips.vips.factory.VipsFactory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("vip")
public class UseKeyCommand extends BaseCommand {

    private final KeyDAO keyDAO;

    public UseKeyCommand(KeyDAO keysDao) {
        this.keyDAO = keysDao;
    }

    @Subcommand("usarkey")
    @Syntax("<key>")
    public void onCommand(Player player, String key) throws SQLException {
        KeyCacheManager keyCacheManager = KeyCacheManager.getInstance();
        KeyCacheMethod keyCacheMethod = KeyCacheMethod.getInstance();

        if (keyCacheMethod.getKey(key) == null) {
            player.sendMessage(PluginImpl.getInstance().Messages.getConfig().getString("commands.keyCommand.keyDoesNotExist"));
            return;
        }

        String vipName = keyCacheMethod.getKeyVipName(key);
        long duration = keyCacheMethod.getKeyVipDuration(key);

        VipUtils vipUtils = VipUtils.getInstance();
        String date = vipUtils.getFormatedDate();

        if (vipUtils.hasVip(String.valueOf(player.getUniqueId()), vipName)) {
            vipUtils.extendVip(player, vipName, duration);
            keyCacheManager.removeKeyData(key);
            keyDAO.deleteKeyData(key);
            return;
        }


        VipsAdapter.getInstance().giveVip(player, date, key, vipName, duration);
        keyCacheManager.removeKeyData(key);
        keyDAO.deleteKeyData(key);
        vipUtils.sendVipMessage(player.getName(), vipName);
    }
}
package blizzard.development.vips.commands.subcommands.keys;

import blizzard.development.vips.database.cache.KeysCacheManager;
import blizzard.development.vips.database.dao.KeysDao;
import blizzard.development.vips.database.storage.KeysData;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.vips.VipUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("vip")
public class UseKey extends BaseCommand {

    private final KeysDao keysDao;

    public UseKey(KeysDao keysDao) {
        this.keysDao = keysDao;
    }

    @Subcommand("usarkey")
    @Syntax("<key>")
    public void onCommand(Player player, String key) throws SQLException {
        KeysCacheManager instance = KeysCacheManager.getInstance();
        KeysData keyData = instance.getKeyData(key);

        if (keyData == null) {
            player.sendMessage(PluginImpl.getInstance().Messages.getConfig().getString("commands.keyCommand.keyDoesNotExist"));
            return;
        }

        String vipName = keyData.getVipName();
        long duration = keyData.getDuration();

        VipUtils vipUtils = VipUtils.getInstance();
        String date = vipUtils.getDate();

        if (vipUtils.hasVip(player.getName(), vipName)) {
            vipUtils.extendVip(player, player.getName(), vipName, vipUtils, duration);
            instance.removeKeyData(key);
            KeysCacheManager.getInstance().cacheKeyData(key, keyData);
            keysDao.removeKeyData(key);
            keysDao.updateKeyData(keyData);
            return;
        }

        vipUtils.giveVip(player, date, key, vipName, duration);
        instance.removeKeyData(key);
        KeysCacheManager.getInstance().cacheKeyData(key, keyData);
        keysDao.removeKeyData(key);
        keysDao.updateKeyData(keyData);
        vipUtils.sendVipMessage(player.getName(), vipName);
    }
}

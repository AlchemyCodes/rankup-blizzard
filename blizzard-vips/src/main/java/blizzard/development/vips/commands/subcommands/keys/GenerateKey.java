package blizzard.development.vips.commands.subcommands.keys;

import blizzard.development.vips.database.cache.KeysCacheManager;
import blizzard.development.vips.database.dao.KeysDao;
import blizzard.development.vips.database.storage.KeysData;
import blizzard.development.vips.utils.PluginImpl;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.utils.vips.VipUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("vip")
public class GenerateKey extends BaseCommand {

    private final KeysDao keysDao;

    public GenerateKey(KeysDao keysDao) {
        this.keysDao = keysDao;
    }

    @Subcommand("gerarkey")
    @CommandPermission("vips.admin")
    @Syntax("<vip> <duracao>")
    @CommandCompletion("@vipName @vipDate")
    public void onCommand(Player player, String vipName, String duration) throws SQLException {
        String vipId = RandomIdGenerator.generateVipId();
        long durationConverted = VipUtils.getInstance().getDuration(duration);

        KeysData keysData = new KeysData(vipId, vipName, durationConverted);

        keysDao.createKeyData(keysData);
        player.sendMessage(PluginImpl.getInstance().Messages.getConfig().getString("commands.keyCommand.keyCreated")
                .replace("{key}", vipId));

        KeysCacheManager.getInstance().cacheKeyData(vipId, keysData);
    }
}

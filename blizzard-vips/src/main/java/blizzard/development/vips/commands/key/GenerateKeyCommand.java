package blizzard.development.vips.commands.key;

import blizzard.development.vips.database.cache.KeyCacheManager;
import blizzard.development.vips.database.dao.KeyDAO;
import blizzard.development.vips.database.storage.KeyData;
import blizzard.development.vips.plugin.PluginImpl;
import blizzard.development.vips.utils.RandomIdGenerator;
import blizzard.development.vips.utils.VipUtils;
import blizzard.development.vips.vips.adapters.VipsAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("vip")
public class GenerateKeyCommand extends BaseCommand {

    private final KeyDAO keyDAO;

    public GenerateKeyCommand(KeyDAO keysDao) {
        this.keyDAO = keysDao;
    }

    @Subcommand("gerarkey")
    @CommandPermission("vips.admin")
    @Syntax("<vip> <duracao>")
    @CommandCompletion("@vipName @vipDate")
    public void onCommand(Player player, String vipName, String duration) {
        VipsAdapter vipsAdapter = VipsAdapter.getInstance();
        String id = RandomIdGenerator.generateId();

        YamlConfiguration messagesConfig = PluginImpl.getInstance().Messages.getConfig();
        if (!vipsAdapter.isVipValid(vipName)) {
            player.sendMessage(messagesConfig.getString("messages.vipNotFound"));
            return;
        }

        long durationConverted = vipsAdapter.getTimeParsed(duration);

        createKeyData(vipName, id, durationConverted);

        player.sendMessage(PluginImpl.getInstance().Messages.getConfig().getString("commands.keyCommand.keyCreated")
                .replace("{key}", id));
    }

    private void createKeyData(String vipName, String id, long durationConverted) {
        KeyData keyData = keyDAO.findKeyData(id);

        if (keyData == null) {
            keyData = new KeyData(id, vipName, durationConverted);
            try {
                keyDAO.createKeyData(keyData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        KeyCacheManager.getInstance().cacheKeyData(id, keyData);
    }
}
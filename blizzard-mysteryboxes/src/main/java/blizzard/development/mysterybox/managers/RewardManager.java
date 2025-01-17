package blizzard.development.mysterybox.managers;

import blizzard.development.mysterybox.mysterybox.enums.MysteryBoxEnum;
import blizzard.development.mysterybox.utils.PluginImpl;
import blizzard.development.mysterybox.utils.text.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RewardManager {

    private static final RewardManager instance = new RewardManager();
    public static RewardManager getInstance() {
        return instance;
    }

    private final Random random = new Random();

    public void reward(Player player, MysteryBoxEnum mysteryBoxEnum) {
        String path = "mysterybox." + mysteryBoxEnum.name().toLowerCase() + ".rewards";

        ConfigurationSection rewardsSection = PluginImpl.getInstance().Rewards.getConfig().getConfigurationSection(path);
        if (rewardsSection == null) {
            player.sendActionBar("§c§lEI! Nenhuma recompensa configurada para " + mysteryBoxEnum.name() + "!");
            return;
        }

        Set<String> rewardKeys = rewardsSection.getKeys(false);
        if (rewardKeys.isEmpty()) {
            player.sendActionBar("§c§lEI! Nenhuma recompensa configurada para " + mysteryBoxEnum.name() + "!");
            return;
        }

        String rewardKey = rewardKeys.toArray(new String[0])[random.nextInt(rewardKeys.size())];
        ConfigurationSection rewardConfig = rewardsSection.getConfigurationSection(rewardKey);

        if (rewardConfig == null) {
            player.sendActionBar("§c§lOcorreu um erro ao obter as recompensas.");
            return;
        }

        String rewardName = rewardConfig.getString("name", "Recompensa Desconhecida");
        List<String> commands = rewardConfig.getStringList("command");

        for (String command : commands) {
            String formattedCommand = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }

        boolean isLegendary = rewardConfig.getBoolean("legendary");

        if (isLegendary) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Component hoverText = TextUtils.parse("§d" + rewardName);
                Component mainMessage = TextUtils.parse("§7(Confira o item ganho passando o mouse por cima)")
                    .hoverEvent(hoverText);

                Component fullMessage = Component.text("\n§d§lCaixa Misteriosa! §8✧ §fO jogador `§7" + player.getName() + "§f` ganhou um item §d§lLENDÁRIO!§f.")
                    .appendNewline()
                    .append(mainMessage)
                    .appendNewline();

                onlinePlayer.sendMessage(fullMessage);
                onlinePlayer.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f);
            }
        }

        player.sendMessage("");
        player.sendMessage(" §d§lYeah! §8✧ §fVocê ganhou: `§7" + rewardName + "§f`!");
        player.sendMessage(" §7Adquira §7mais §lcaixas§7 em §f§owww.alchemynetwork.net§7.");
        player.sendMessage("");
    }
}

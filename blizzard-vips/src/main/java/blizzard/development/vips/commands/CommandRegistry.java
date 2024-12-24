package blizzard.development.vips.commands;

import blizzard.development.vips.Main;
import blizzard.development.vips.commands.vips.ChangeVip;
import blizzard.development.vips.commands.vips.ConsultVip;
import blizzard.development.vips.commands.vips.GiveVip;
import blizzard.development.vips.commands.vips.RemoveVip;
import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.PlayersDAO;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandRegistry {

    public void register() {
        PlayersDAO playersDAO = new PlayersDAO();

        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new GiveVip(playersDAO),
                new ConsultVip(),
                new RemoveVip(),
                new ChangeVip()
        ).forEach(paperCommandManager::registerCommand);

        registerCompletions(paperCommandManager);
    }

    private void registerCompletions(PaperCommandManager manager) {
        manager.getCommandCompletions().registerAsyncCompletion
                ("vipIds", c -> PlayersCacheManager.getInstance().getAllVipIds());

        manager.getCommandCompletions().registerAsyncCompletion
                ("playerName",
                        c -> Main.getInstance().getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));

        String[] vipNames = {"ouro", "diamante", "esmeralda"};
        manager.getCommandCompletions().registerAsyncCompletion
                ("vipName", c -> List.of(vipNames));

        String[] vipDates = {"1s", "1m", "1h", "1d"};
        manager.getCommandCompletions().registerAsyncCompletion
                ("vipDate", c -> List.of(vipDates));
    }
}

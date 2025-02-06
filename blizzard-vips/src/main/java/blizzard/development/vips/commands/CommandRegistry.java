package blizzard.development.vips.commands;

import blizzard.development.vips.Main;
import blizzard.development.vips.commands.subcommands.freeze.FreezeVip;
import blizzard.development.vips.commands.subcommands.keys.GenerateKey;
import blizzard.development.vips.commands.subcommands.keys.UseKey;
import blizzard.development.vips.commands.vips.ChangeVip;
import blizzard.development.vips.commands.vips.ConsultVip;
import blizzard.development.vips.commands.vips.GiveVip;
import blizzard.development.vips.commands.vips.RemoveVip;
import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.KeysDao;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.enums.VipEnum;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandRegistry {

    public void register() {
        KeysDao keysDAO = new KeysDao();

        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new GiveVip(),
                new ConsultVip(),
                new RemoveVip(),
                new ChangeVip(),
                new GenerateKey(keysDAO),
                new UseKey(keysDAO),
                new FreezeVip()
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

        manager.getCommandCompletions().registerAsyncCompletion
                ("vipName",
                        c -> Arrays.stream(VipEnum.values())
                        .map(VipEnum::getName)
                        .collect(Collectors.toList())
        );

        String[] vipDates = {"1s", "1m", "1h", "1d"};
        manager.getCommandCompletions().registerAsyncCompletion
                ("vipDate", c -> List.of(vipDates));
    }
}

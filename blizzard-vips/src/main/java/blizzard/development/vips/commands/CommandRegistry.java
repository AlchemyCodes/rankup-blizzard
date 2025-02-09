package blizzard.development.vips.commands;

import blizzard.development.vips.Main;
import blizzard.development.vips.commands.key.GenerateKeyCommand;
import blizzard.development.vips.commands.key.UseKeyCommand;
import blizzard.development.vips.commands.vips.ChangeVipCommand;
import blizzard.development.vips.commands.vips.ConsultVipCommand;
import blizzard.development.vips.commands.subcommands.FreezeVipCommand;
import blizzard.development.vips.commands.vips.GiveVipCommand;
import blizzard.development.vips.database.dao.KeyDAO;
import blizzard.development.vips.vips.enums.VipEnum;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandRegistry {

    public void register() {
        KeyDAO keyDAO = new KeyDAO();

        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new GenerateKeyCommand(keyDAO),
                new UseKeyCommand(keyDAO),
                new ConsultVipCommand(),
                new GiveVipCommand(),
                new FreezeVipCommand(),
                new ChangeVipCommand()
        ).forEach(paperCommandManager::registerCommand);

        registerCompletions(paperCommandManager);
    }

    private void registerCompletions(PaperCommandManager manager) {
//        manager.getCommandCompletions().registerAsyncCompletion
//                ("vipIds", c -> PlayersCacheManager.getInstance().getAllVipIds());

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
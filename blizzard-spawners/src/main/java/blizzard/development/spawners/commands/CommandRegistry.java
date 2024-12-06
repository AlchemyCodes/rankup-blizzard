package blizzard.development.spawners.commands;

import blizzard.development.spawners.commands.spawners.subcommands.GiveCommand;
import blizzard.development.spawners.commands.spawners.subcommands.RankingCommand;
import blizzard.development.spawners.commands.spawners.subcommands.ReloadCommand;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandRegistry {

    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new GiveCommand(),
                new ReloadCommand(),
                new RankingCommand()
        ).forEach(paperCommandManager::registerCommand);

        paperCommandManager.getCommandCompletions().registerCompletion("spawners", c -> Spawners.getAllTypes());

        paperCommandManager.getCommandCompletions().registerCompletion("amount", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 1; i < 101; i++) {
                array.add(String.valueOf(i));
            }
            return array;
        });
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}


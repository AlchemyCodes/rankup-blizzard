package blizzard.development.spawners.commands;

import blizzard.development.spawners.commands.slaughterhouses.SlaughterhousesCommand;
import blizzard.development.spawners.commands.spawners.SpawnersCommand;
import blizzard.development.spawners.commands.spawners.subcommands.*;
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
                // spawners
                new LockCommand(),
                new UnlockCommand(),
                new GiveCommand(),
                new ReloadCommand(),
                new RankingCommand(),
                new SpawnersCommand(),
                new LimitsCommand(),
                new AutoSellCommand(),
                // slaughterhouses
                new SlaughterhousesCommand(),
                new blizzard.development.spawners.commands.slaughterhouses.subcommands.ReloadCommand()
        ).forEach(paperCommandManager::registerCommand);

        paperCommandManager.getCommandCompletions().registerCompletion("spawners", c -> Spawners.getAllTypes());
        paperCommandManager.getCommandCompletions().registerCompletion("limits", c -> {
            ArrayList<String> array = new ArrayList<>();

            array.add("compra");
            array.add("amigos");

            return array;
        });
        paperCommandManager.getCommandCompletions().registerCompletion("actions", c -> {
            ArrayList<String> array = new ArrayList<>();

            array.add("dar");
            array.add("adicionar");
            array.add("remover");

            return array;
        });
        paperCommandManager.getCommandCompletions().registerCompletion("actionssell", c -> {
            ArrayList<String> array = new ArrayList<>();

            array.add("dar");

            return array;
        });
        paperCommandManager.getCommandCompletions().registerCompletion("amount", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 1; i < 101; i++) {
                array.add(String.valueOf(i));
            }
            return array;
        });
        paperCommandManager.getCommandCompletions().registerCompletion("slaughterhouseslevels", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
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


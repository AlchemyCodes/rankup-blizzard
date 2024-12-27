package blizzard.development.monsters.commands;
import blizzard.development.monsters.commands.subcommands.admins.ReloadCommand;
import blizzard.development.monsters.commands.main.MonstersCommand;
import blizzard.development.monsters.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandRegistry {

    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                // admins
                new ReloadCommand(),

                // users
                new MonstersCommand()
        ).forEach(paperCommandManager::registerCommand);

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


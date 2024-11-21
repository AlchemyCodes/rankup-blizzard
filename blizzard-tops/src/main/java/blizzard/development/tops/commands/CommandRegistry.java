package blizzard.development.tops.commands;

import blizzard.development.tops.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandRegistry {
    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new TopsCommand()
        ).forEach(paperCommandManager::registerCommand);
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}

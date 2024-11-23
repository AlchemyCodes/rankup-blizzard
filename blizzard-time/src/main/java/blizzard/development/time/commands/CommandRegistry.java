package blizzard.development.time.commands;

import blizzard.development.time.commands.time.TimeCommand;
import blizzard.development.time.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {
    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new TimeCommand()
        ).forEach(paperCommandManager::registerCommand);
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}

package blizzard.development.spawners.commands;

import blizzard.development.spawners.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
        ).forEach(paperCommandManager::registerCommand);
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}


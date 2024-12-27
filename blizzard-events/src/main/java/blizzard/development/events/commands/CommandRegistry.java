package blizzard.development.events.commands;

import blizzard.development.events.Main;
import blizzard.development.events.commands.events.SumoCommand;
import blizzard.development.events.commands.subcommands.SumoSubCommands;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new EventsCommand(),
                new SumoCommand(),
                new ReloadConfig(),
                new SumoSubCommands()
        ).forEach(paperCommandManager::registerCommand);
    }
}

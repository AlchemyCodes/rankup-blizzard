package blizzard.development.mine.commands;

import blizzard.development.mine.commands.mine.MineCommand;
import blizzard.development.mine.commands.mine.subcommands.admins.LocationsCommand;
import blizzard.development.mine.commands.mine.subcommands.admins.SettersCommand;
import blizzard.development.mine.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new MineCommand(),
                new LocationsCommand(),
                new SettersCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}

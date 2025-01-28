package blizzard.development.mine.commands;

import blizzard.development.mine.commands.mine.MineCommand;
import blizzard.development.mine.commands.mine.subcommands.admins.*;
import blizzard.development.mine.commands.mine.subcommands.users.ResetCommand;
import blizzard.development.mine.managers.events.AvalancheManager;
import blizzard.development.mine.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                // admins
                new MineCommand(),
                new ReloadCommand(),
                new SettersCommand(),
                new LocationsCommand(),
                new VisibilityCommand(),
                new DisplayCommand(),
                //users
                new ResetCommand(),
                new AvalancheManager()
        ).forEach(paperCommandManager::registerCommand);
    }
}

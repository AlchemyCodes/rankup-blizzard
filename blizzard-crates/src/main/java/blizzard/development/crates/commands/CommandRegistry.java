package blizzard.development.crates.commands;

import blizzard.development.crates.Main;
import blizzard.development.crates.commands.staff.CrateCommand;
import blizzard.development.crates.commands.staff.KeyCommand;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new CrateCommand(),
                new KeyCommand(),
                new DebugCommand()
        ).forEach(paperCommandManager::registerCommand);
    }
}

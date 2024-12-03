package blizzard.development.essentials.commands;

import blizzard.development.essentials.Main;
import blizzard.development.essentials.commands.commons.*;
import blizzard.development.essentials.commands.staff.*;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    public void register() {
        PaperCommandManager commandManager = new PaperCommandManager(Main.getInstance());

        Arrays.asList(
                new CraftCommand(),
                new EchestCommand(),
                new PingCommand(),
                new SpawnCommand(),
                new TrashCommand(),
                new WarpCommand(),
                new ClearChatCommand(),
                new ClearCommand(),
                new GameModeCommand(),
                new HealCommand(),
                new SetSpawnCommand(),
                new SetWarpCommand(),
                new SpeedCommand(),
                new TpallCommand(),
                new VanishCommand(),
                new TpCommand(),
                new TpToCommand(),
                new LightCommand(),
                new FeedCommand(),
                new FlyCommand(),
                new AnnounceCommand(),
                new SudoCommand(),
                new CrashCommand(),
                new InvseeCommand(),
                new BackCommand(),
                new HomeCommand(),
                new SetHomeCommand(),
                new DelHomeCommand(),
                new TpaCommand(),
                new TpaDenyCommand(),
                new TpaAcceptCommand(),
                new TpHereCommand()
        ).forEach(commandManager::registerCommand);
    }
}

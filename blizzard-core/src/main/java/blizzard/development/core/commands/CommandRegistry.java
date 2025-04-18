package blizzard.development.core.commands;

import blizzard.development.core.Main;
import blizzard.development.core.commands.clothings.ClothingCommand;
import blizzard.development.core.commands.subcommands.SetsCommand;
import blizzard.development.core.commands.temperature.TemperatureCommand;
import java.util.Arrays;
import java.util.Objects;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;

public class CommandRegistry {
    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(Main.getInstance());

        Objects.requireNonNull(paperCommandManager);
        Arrays.asList(new BaseCommand[] {
                new ClothingCommand(),
                        new TemperatureCommand(),
                        new SetsCommand(),
                        new ActiveCoreDebug(),
                        new ReloadConfig()
        })
                .forEach(paperCommandManager::registerCommand);
    }
}

package blizzard.development.currencies.commands;

import blizzard.development.currencies.commands.currencies.CurrenciesCommand;
import blizzard.development.currencies.commands.currencies.bosses.SoulsCommand;
import blizzard.development.currencies.commands.currencies.bosses.subcommands.SoulsExchangeCommand;
import blizzard.development.currencies.commands.currencies.excavation.FossilsCommand;
import blizzard.development.currencies.commands.currencies.excavation.subcommands.FossilsExchangeCommand;
import blizzard.development.currencies.commands.currencies.rankup.FlakesCommand;
import blizzard.development.currencies.commands.currencies.rankup.subcommands.FlakesExchangeCommand;
import blizzard.development.currencies.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.Arrays;

public class CommandRegistry {

    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                new CurrenciesCommand(),
                new SoulsCommand(),
                new SoulsExchangeCommand(),
                new FossilsCommand(),
                new FossilsExchangeCommand(),
                new FlakesCommand(),
                new FlakesExchangeCommand()

        ).forEach(paperCommandManager::registerCommand);
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}


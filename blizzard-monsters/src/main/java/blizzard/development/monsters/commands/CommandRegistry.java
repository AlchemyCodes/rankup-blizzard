package blizzard.development.monsters.commands;
import blizzard.development.monsters.commands.subcommands.admins.GiveCommand;
import blizzard.development.monsters.commands.subcommands.admins.LocationCommand;
import blizzard.development.monsters.commands.subcommands.admins.ReloadCommand;
import blizzard.development.monsters.commands.main.MonstersCommand;
import blizzard.development.monsters.commands.subcommands.users.JoinCommand;
import blizzard.development.monsters.commands.subcommands.users.LeaveCommand;
import blizzard.development.monsters.commands.subcommands.users.SwordCommand;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.utils.PluginImpl;
import co.aikar.commands.PaperCommandManager;

import java.util.*;

public class CommandRegistry {

    private static CommandRegistry instance;

    public void register() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(PluginImpl.getInstance().plugin);

        Arrays.asList(
                // admins
                new ReloadCommand(),
                new LocationCommand(),
                new GiveCommand(),

                // users
                new MonstersCommand(),
                new JoinCommand(),
                new LeaveCommand(),
                new SwordCommand()
        ).forEach(paperCommandManager::registerCommand);

        paperCommandManager.getCommandCompletions().registerCompletion("monsters", set -> {
            Set<String> monsters = MonstersHandler.getInstance().getAll();
            if (monsters != null && !monsters.isEmpty()) {
                return monsters;
            }
            return List.of("Nenhum");
        });

        paperCommandManager.getCommandCompletions().registerCompletion("location_actions", c -> {
            ArrayList<String> array = new ArrayList<>();
            array.add("entrada");
            array.add("saida");
            return array;
        });

        paperCommandManager.getCommandCompletions().registerCompletion("amount", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 1; i < 101; i++) {
                array.add(String.valueOf(i));
            }
            return array;
        });
    }

    public static CommandRegistry getInstance() {
        if (instance == null) instance = new CommandRegistry();
        return instance;
    }
}


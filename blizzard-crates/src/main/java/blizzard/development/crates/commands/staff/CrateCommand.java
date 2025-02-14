package blizzard.development.crates.commands.staff;

import blizzard.development.crates.Main;
import blizzard.development.crates.builder.ItemBuilder;
import co.aikar.commands.annotation.*;
import co.aikar.commands.BaseCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static blizzard.development.crates.managers.CrateManager.removeCrate;

@CommandAlias("crates|caixas")
public class CrateCommand extends BaseCommand {

    @Subcommand("givecrate")
    @CommandPermission("alchemy.crates.crate")
    @Syntax("<player> <crate>")
    public void onGive(CommandSender sender, String playerTarget, String crateType) {


        Player target = Bukkit.getPlayer(playerTarget);
        if (target == null) {
            sender.sendActionBar(Component.text("§c§lEI! §cO jogador não existe ou está offline."));
            return;
        }

        switch (crateType) {
            case "comum":

                List<String> commomLore = new ArrayList<>();
                commomLore.add("§7Invoque a caixa");
                commomLore.add("§7comum.");
                commomLore.add("");
                commomLore.add("§8Clique para colocar.");

                target.getInventory().addItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODAxMzk4YTY5MWFiYjg1NWNjZTFjY2I2YmQzYmFkZTUwYmEyNWJlMDEyY2FiZWYzMWZiZGYwM2FlNDliYzg4NSJ9fX0=")
                    .setDisplayName("§8Caixa §lComum!")
                    .setLore(commomLore)
                    .addPersistentData(Main.getInstance(), "comum", "comum")
                    .build());

                break;
            case "rara":

                List<String> rareLore = new ArrayList<>();
                rareLore.add("§7Invoque a caixa");
                rareLore.add("§7rara.");
                rareLore.add("");
                rareLore.add("§aClique para colocar.");

                target.getInventory().addItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIwODRlODlhNzBkNzA5OGI2OTQ4MzRlNTM0OTY3NDE1NjcwYjgwODM3YWZiOWJiOWY0YTI2ZmZiNzk3Zjg5MSJ9fX0=")
                    .setDisplayName("§aCaixa §lRara!")
                    .setLore(rareLore)
                    .addPersistentData(Main.getInstance(), "rara", "rara")
                    .build());

                break;
            case "mistica":

                List<String> mysticLore = new ArrayList<>();
                mysticLore.add("§7Invoque a caixa");
                mysticLore.add("§7mística");
                mysticLore.add("");
                mysticLore.add("§5Clique para colocar.");

                target.getInventory().addItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM0NmUyZTkzNmRjY2IwZWRjNzAzNmJiNWRjYjEyNjJhYzAwMDYzODUxNWVmZTM0ZmZiOTdkYjY4NzY0NjIyMSJ9fX0=")
                    .setDisplayName("§5Caixa §lMística!")
                    .setLore(mysticLore)
                    .addPersistentData(Main.getInstance(), "mistica", "mistica")
                    .build());

                break;
            case "lendaria":

                List<String> legendaryLore = new ArrayList<>();
                legendaryLore.add("§7Invoque a caixa");
                legendaryLore.add("§7lendária.");
                legendaryLore.add("");
                legendaryLore.add("§6Clique para colocar.");

               target.getInventory().addItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBmYzk4MTk1MDBmNmQxMWEwM2IzMjJhMmZmM2I2ZjU1NDg4Y2MyMzI4ODRhZDZmNDczNjZmZTU4MGEzYmE0ZiJ9fX0=")
                    .setDisplayName("§6Caixa §lLendária!")
                    .setLore(legendaryLore)
                    .addPersistentData(Main.getInstance(), "lendaria", "lendaria")
                    .build());

                break;
            case "blizzard":

                List<String> blizzardLore = new ArrayList<>();
                blizzardLore.add("§7Invoque a caixa");
                blizzardLore.add("§7blizzard.");
                blizzardLore.add("");
                blizzardLore.add("§bClique para colocar.");

                target.getInventory().addItem(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMzMzZkNzQxNjI1ZGMwYWY0NjAyNGE4YzY1NDMwN2U1Njk1ZWZkMzc5YjE1OTY3OGFlNjVjZmEwNGEyMzkxNCJ9fX0=")
                    .setDisplayName("§bCaixa §lBlizzard!")
                    .setLore(blizzardLore)
                    .addPersistentData(Main.getInstance(), "blizzard", "blizzard")
                    .build());

                break;
            case "remove":
                removeCrate();
                sender.sendMessage("removido");
                break;
            default:
                sender.sendMessage("");
                sender.sendMessage(" §c§lEI! §cA caixa §7" + crateType + "§c não existe.");
                sender.sendMessage(" §cDisponíveis: §7[comum, rara, mistica, lendaria e blizzard]");
                sender.sendMessage("");
        }
    }

}

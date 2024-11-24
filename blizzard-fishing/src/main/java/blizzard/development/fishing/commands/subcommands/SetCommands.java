package blizzard.development.fishing.commands.subcommands;

import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.enums.RodMaterials;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("pesca")
public class SetCommands extends BaseCommand {

    private final PlayersCacheMethod playersMethod = PlayersCacheMethod.getInstance();
    private final RodsCacheMethod rodsMethod = RodsCacheMethod.getInstance();

    @Subcommand("darpeixe")
    @Syntax("<jogador> <peixe> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setFishes(String targetName, String fish, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            switch (fish.toLowerCase()) {
                case "bacalhau" -> playersMethod.setBacalhau(target, amount);
                case "salmao" -> playersMethod.setSalmao(target, amount);
                case "caranguejo" -> playersMethod.setCaranguejo(target, amount);
                case "lagosta" -> playersMethod.setLagosta(target, amount);
                case "lula" -> playersMethod.setLula(target, amount);
                case "lula_brilhante" -> playersMethod.setLulaBrilhante(target, amount);
                case "tubarao" -> playersMethod.setTubarao(target, amount);
                case "baleia" -> playersMethod.setBaleia(target, amount);
                default -> target.sendMessage("Tipo de peixe inválido.");
            }
            target.sendMessage("O valor de " + fish + " foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setarmazem")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setStorage(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            playersMethod.setStorage(target, amount);
            target.sendMessage("O tamanho de armazenamento foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setlixo")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setTrash(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            playersMethod.setTrash(target, amount);
            target.sendMessage("O tamanho de lixo foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setforca")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setStrength(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            rodsMethod.setStrength(target, amount);
            target.sendMessage("força foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setxp")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setXp(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            rodsMethod.setXp(target, amount);
            target.sendMessage("xp foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setexperiente")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setExperienced(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            rodsMethod.setExperienced(target, amount);
            target.sendMessage("experienced foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("setsorte")
    @Syntax("<jogador> <quantidade>")
    @CommandPermission("pesca.admin")
    public void setLucky(String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            rodsMethod.setLucky(target, amount);
            target.sendMessage("lucky foi atualizado para " + amount + ".");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    @Subcommand("giveskin")
    @Syntax("<player> <skin>")
    @CommandPermission("pesca.admin")
    public void onGiveSkin(Player player, String target, RodMaterials material) {
        Player playerTarget = Bukkit.getPlayer(target);

        if (playerTarget == null) {
            player.sendMessage("§cO jogador especificado não está online ou não existe.");
            return;
        }

        if (rodsMethod.getMaterials(playerTarget).contains(material)) {
            player.sendMessage("§cO jogador já possui este material.");
        } else {
            rodsMethod.addMaterial(playerTarget, material);
            player.sendMessage("§fVocê deu o material " + material.name() + " para o jogador: " + playerTarget.getName() + "!");
        }
    }
}

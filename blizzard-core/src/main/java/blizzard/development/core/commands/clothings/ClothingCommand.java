/*    */ package blizzard.development.core.commands.clothings;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.CommandPermission;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import blizzard.development.essentials.acf.annotation.Syntax;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ @CommandAlias("manto|mantos|traje|trajes|roupa|roupas")
/*    */ public class ClothingCommand
/*    */   extends BaseCommand
/*    */ {
/*    */   @Default
/*    */   @CommandPermission("alchemy.core.staff")
/*    */   @Syntax("<jogador> <manto> <quantia>")
/*    */   public void onCommand(CommandSender commandSender, String target, String clothing, int amount) {
/* 25 */     Player player = Bukkit.getPlayer(target);
/*    */     
/* 27 */     if (player == null) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     switch (clothing) {
/*    */       case "comum":
/* 33 */         (new ItemBuilder(Material.LEATHER_CHESTPLATE))
/* 34 */           .setDisplayName("§cManto de Couro §7[Ativador]")
/* 35 */           .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */ 
/*    */                 
/*    */                 "§7Ative o manto para se", "§7proteger contra o fio", "", " <#e8e9eb>Este manto irá fornecer<#e4e7ed>", " <#e8e9eb>5% de proteção ao frio.<#e4e7ed>", "", "§cClique para ativar."
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 44 */               })).addPersistentData((Plugin)Main.getInstance(), "ativador.comum")
/* 45 */           .build(player, amount);
/*    */         return;
/*    */       case "rara":
/* 48 */         (new ItemBuilder(Material.CHAINMAIL_CHESTPLATE))
/* 49 */           .setDisplayName("<#bec4c2>Manto de Malha<#bec4c2> §7[Ativador]")
/* 50 */           .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */ 
/*    */                 
/*    */                 "§7Ative o manto para se", "§7proteger contra o fio", "", " <#c4c4c4>Este manto irá fornecer<#bec4c2>", " <#c4c4c4>15% de proteção ao frio.<#bec4c2>", "", "<#c4c4c4>Clique para ativar.<#c4c4c4>"
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 59 */               })).addPersistentData((Plugin)Main.getInstance(), "ativador.raro")
/* 60 */           .build(player, amount);
/*    */         return;
/*    */       case "mistica":
/* 63 */         (new ItemBuilder(Material.IRON_CHESTPLATE))
/* 64 */           .setDisplayName("<#e6e3dc>Manto de Ferro<#e6e3dc> §7[Ativador]")
/* 65 */           .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */ 
/*    */                 
/*    */                 "§7Ative o manto para se", "§7proteger contra o fio", "", " <#e6e3dc>Este manto irá fornecer<#bec4c2>", " <#e6e3dc>25% de proteção ao frio.<#bec4c2>", "", "<#e6e3dc>Clique para ativar.<#e6e3dc>"
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 74 */               })).addPersistentData((Plugin)Main.getInstance(), "ativador.mistico")
/* 75 */           .build(player, amount);
/*    */         return;
/*    */       case "lendaria":
/* 78 */         (new ItemBuilder(Material.DIAMOND_CHESTPLATE))
/* 79 */           .setDisplayName("§bManto de Diamante §7[Ativador]")
/* 80 */           .setLore(Arrays.asList(new String[] {
/*    */ 
/*    */ 
/*    */                 
/*    */                 "§7Ative o manto para se", "§7proteger contra o fio", "", " <#e6e3dc>Este manto irá fornecer<#bec4c2>", " <#e6e3dc>55% de proteção ao frio.<#bec4c2>", "", "§bClique para ativar."
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 89 */               })).addPersistentData((Plugin)Main.getInstance(), "ativador.lendario")
/* 90 */           .build(player, amount);
/*    */         return;
/*    */     } 
/* 93 */     commandSender.sendMessage("");
/* 94 */     commandSender.sendMessage(" §c§lEI §cO manto §7" + clothing + "§c não existe.");
/* 95 */     commandSender.sendMessage(" §cDisponíveis: §7[comum, rara, mistica e lendaria]");
/* 96 */     commandSender.sendMessage("");
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\commands\clothings\ClothingCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
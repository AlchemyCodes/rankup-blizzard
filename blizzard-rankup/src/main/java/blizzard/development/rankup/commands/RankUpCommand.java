/*    */ package blizzard.development.rankup.commands;
/*    */ 
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import blizzard.development.rankup.inventories.ConfirmationInventory;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @CommandAlias("rankup")
/*    */ public class RankUpCommand
/*    */   extends BaseCommand {
/*    */   @Default
/*    */   public void onCommand(Player player) {
/* 14 */     ConfirmationInventory.openConfirmationInventory(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\commands\RankUpCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
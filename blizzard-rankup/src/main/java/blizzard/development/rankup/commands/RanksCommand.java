/*    */ package blizzard.development.rankup.commands;
/*    */ 
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import blizzard.development.rankup.inventories.RanksInventory;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @CommandAlias("ranks")
/*    */ public class RanksCommand
/*    */   extends BaseCommand {
/*    */   @Default
/*    */   public void onCommand(Player player) {
/* 14 */     RanksInventory.openRanksInventory(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\commands\RanksCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
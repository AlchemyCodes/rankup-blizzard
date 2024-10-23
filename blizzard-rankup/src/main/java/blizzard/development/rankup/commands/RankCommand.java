/*    */ package blizzard.development.rankup.commands;
/*    */ 
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import blizzard.development.rankup.inventories.RankInventory;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @CommandAlias("rank")
/*    */ public class RankCommand
/*    */   extends BaseCommand
/*    */ {
/*    */   @Default
/*    */   public void onCommand(Player player) {
/* 15 */     RankInventory.openRankInventory(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\rankup\commands\RankCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
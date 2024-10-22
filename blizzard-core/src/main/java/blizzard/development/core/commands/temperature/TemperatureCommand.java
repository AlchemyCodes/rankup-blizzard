/*    */ package blizzard.development.core.commands.temperature;
/*    */ 
/*    */ import blizzard.development.core.database.cache.PlayersCacheManager;
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.annotation.CommandAlias;
/*    */ import blizzard.development.essentials.acf.annotation.Default;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @CommandAlias("temperature")
/*    */ public class TemperatureCommand
/*    */   extends BaseCommand {
/*    */   @Default
/*    */   public void onCommand(Player player, double temperature) {
/* 14 */     PlayersCacheManager.setTemperature(player, temperature);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\commands\temperature\TemperatureCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
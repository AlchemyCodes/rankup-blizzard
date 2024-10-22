/*    */ package blizzard.development.core.commands;
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.commands.clothings.ClothingCommand;
/*    */ import blizzard.development.core.commands.temperature.TemperatureCommand;
/*    */ import blizzard.development.essentials.acf.BaseCommand;
/*    */ import blizzard.development.essentials.acf.PaperCommandManager;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class CommandRegistry {
/*    */   public void register() {
/* 13 */     PaperCommandManager paperCommandManager = new PaperCommandManager((Plugin)Main.getInstance());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 18 */     Objects.requireNonNull(paperCommandManager); Arrays.<BaseCommand>asList(new BaseCommand[] { (BaseCommand)new ClothingCommand(), (BaseCommand)new TemperatureCommand() }).forEach(paperCommandManager::registerCommand);
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\commands\CommandRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
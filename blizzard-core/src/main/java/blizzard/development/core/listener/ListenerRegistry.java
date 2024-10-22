/*    */ package blizzard.development.core.listener;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.database.dao.PlayersDAO;
/*    */ import blizzard.development.core.listener.campfire.CampfirePlace;
/*    */ import blizzard.development.core.listener.clothing.ClothingActivatorInteractEvent;
/*    */ import blizzard.development.core.listener.clothing.ClothingInventoryEvent;
/*    */ import blizzard.development.core.listener.geral.TrafficListener;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class ListenerRegistry {
/*    */   private final PlayersDAO playersDAO;
/*    */   
/*    */   public ListenerRegistry(PlayersDAO playersDAO) {
/* 19 */     this.playersDAO = playersDAO;
/*    */   }
/*    */   
/*    */   public void register() {
/* 23 */     PluginManager pluginManager = Bukkit.getPluginManager();
/*    */     
/* 25 */     Arrays.<Listener>asList(new Listener[] { (Listener)new TrafficListener(this.playersDAO), (Listener)new ClothingActivatorInteractEvent(), (Listener)new ClothingInventoryEvent(), (Listener)new CampfirePlace()
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 30 */         }).forEach(listener -> pluginManager.registerEvents(listener, (Plugin)Main.getInstance()));
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\listener\ListenerRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
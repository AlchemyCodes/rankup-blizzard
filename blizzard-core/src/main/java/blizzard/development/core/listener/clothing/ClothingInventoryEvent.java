/*    */ package blizzard.development.core.listener.clothing;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ClothingInventoryEvent
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onInteract(InventoryClickEvent event) {
/* 15 */     ItemStack itemStack = event.getCurrentItem();
/*    */     
/* 17 */     if (itemStack == null)
/*    */       return; 
/* 19 */     String[] clothingsData = { "manto.couro", "manto.malha", "manto.ferro", "manto.diamante" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     for (String clothing : clothingsData) {
/* 27 */       if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), itemStack, clothing))
/* 28 */         event.setCancelled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\listener\clothing\ClothingInventoryEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
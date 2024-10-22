/*    */ package blizzard.development.core.listener.clothing;
/*    */ 
/*    */ import blizzard.development.core.Main;
/*    */ import blizzard.development.core.builder.ItemBuilder;
/*    */ import blizzard.development.core.inventories.SelectInventory;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ClothingActivatorInteractEvent
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onInteract(PlayerInteractEvent event) {
/* 18 */     Player player = event.getPlayer();
/* 19 */     ItemStack item = event.getItem();
/*    */     
/* 21 */     if (item == null || item.getType() == Material.AIR)
/*    */       return; 
/* 23 */     String[] activators = { "ativador.comum", "ativador.raro", "ativador.mistico", "ativador.lendario" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     for (String activator : activators) {
/* 31 */       if (ItemBuilder.hasPersistentData((Plugin)Main.getInstance(), item, activator)) {
/* 32 */         event.setCancelled(true);
/*    */         
/* 34 */         SelectInventory selectInventory = new SelectInventory();
/* 35 */         selectInventory.open(player, item);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\listener\clothing\ClothingActivatorInteractEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
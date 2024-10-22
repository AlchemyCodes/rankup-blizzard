/*    */ package blizzard.development.core.listener.campfire;
/*    */ 
/*    */ import blizzard.development.core.managers.CampfireManager;
/*    */ import blizzard.development.core.tasks.BlizzardTask;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ public class CampfirePlace
/*    */   implements Listener {
/*    */   @EventHandler
/*    */   public void onBlockPlace(BlockPlaceEvent event) {
/* 16 */     Player player = event.getPlayer();
/* 17 */     Block block = event.getBlock();
/*    */     
/* 19 */     if (block.getType() == Material.CAMPFIRE) {
/*    */       
/* 21 */       if (!BlizzardTask.isSnowing) {
/* 22 */         event.setCancelled(true);
/*    */         
/* 24 */         player.sendMessage("vc nao precisa se aquecer agora!");
/*    */         
/*    */         return;
/*    */       } 
/* 28 */       if (CampfireManager.hasCampfire(player)) {
/* 29 */         event.setCancelled(true);
/*    */         
/* 31 */         player.sendMessage("vc ja tem uma fogueira!");
/*    */         
/*    */         return;
/*    */       } 
/* 35 */       CampfireManager.placeCampfire(player, block);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\listener\campfire\CampfirePlace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
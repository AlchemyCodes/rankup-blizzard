/*    */ package blizzard.development.core.managers;
/*    */ 
/*    */ import com.comphenix.protocol.wrappers.BlockPosition;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CampfireManager
/*    */ {
/* 19 */   private static final Map<Player, Boolean> playerCampfireMap = new ConcurrentHashMap<>();
/* 20 */   private static final Map<Player, BlockPosition> playerCampfirePosition = new ConcurrentHashMap<>();
/*    */   
/*    */   public static void placeCampfire(Player player, Block block) {
/* 23 */     BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
/*    */     
/* 25 */     playerCampfireMap.put(player, Boolean.valueOf(true));
/* 26 */     playerCampfirePosition.put(player, blockPosition);
/*    */   }
/*    */   
/*    */   public static void removeCampfire(Player player) {
/* 30 */     if (!((Boolean)playerCampfireMap.getOrDefault(player, Boolean.valueOf(false))).booleanValue())
/*    */       return; 
/* 32 */     playerCampfireMap.remove(player);
/*    */   }
/*    */   
/*    */   public static boolean hasCampfire(Player player) {
/* 36 */     return ((Boolean)playerCampfireMap.getOrDefault(player, Boolean.valueOf(false))).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\managers\CampfireManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
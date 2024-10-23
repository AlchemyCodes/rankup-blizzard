/*    */ package blizzard.development.rankup.utils.skulls;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.Base64;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SkullAPI
/*    */ {
/*    */   public static ItemStack withName(ItemStack item, String name) {
/* 14 */     notNull(item, "item");
/* 15 */     notNull(name, "name");
/* 16 */     return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:\"" + name + "\"}");
/*    */   }
/*    */   public static ItemStack fromUrl(Type type, String url) {
/* 19 */     ItemStack item = new ItemStack(type.mat, 1, (short)3);
/* 20 */     return withUrl(item, url);
/*    */   }
/*    */   public static ItemStack withUrl(ItemStack item, String url) {
/*    */     URI actualUrl;
/* 24 */     notNull(url, "url");
/*    */     
/*    */     try {
/* 27 */       actualUrl = new URI(url);
/* 28 */     } catch (URISyntaxException e) {
/* 29 */       throw new RuntimeException(e);
/*    */     } 
/* 31 */     String base64 = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
/* 32 */     base64 = Base64.getEncoder().encodeToString(base64.getBytes());
/* 33 */     return withBase64(item, base64);
/*    */   }
/*    */   public static ItemStack fromBase64(Type type, String base64) {
/* 36 */     ItemStack item = new ItemStack(type.mat, 1, (short)3);
/* 37 */     return withBase64(item, base64);
/*    */   }
/*    */   public static ItemStack withBase64(ItemStack item, String base64) {
/* 40 */     notNull(item, "item");
/* 41 */     notNull(base64, "base64");
/* 42 */     UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
/* 43 */     return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}");
/*    */   }
/*    */   
/*    */   public enum Type {
/* 47 */     BLOCK((String)Material.PLAYER_HEAD),
/* 48 */     ITEM((String)Material.PLAYER_HEAD);
/*    */     
/*    */     private Material mat;
/*    */ 
/*    */     
/*    */     Type(Material mat) {
/* 54 */       this.mat = mat;
/*    */     }
/*    */   }
/*    */   
/*    */   private static void notNull(Object o, String name) {
/* 59 */     if (o == null)
/* 60 */       throw new NullPointerException(name + " should not be null!"); 
/*    */   }
/*    */   
/*    */   public static ItemStack createSkullItem(String texture, String name, String... lore) {
/* 64 */     return (new SkullBuilder(texture))
/* 65 */       .name(name)
/* 66 */       .lore(lore)
/* 67 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\skulls\SkullAPI.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
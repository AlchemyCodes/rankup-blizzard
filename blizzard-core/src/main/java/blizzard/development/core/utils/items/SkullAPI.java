/*    */ package blizzard.development.core.utils.items;
/*    */ 
/*    */ import blizzard.development.core.builder.skull.SkullBuilder;
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
/* 15 */     notNull(item, "item");
/* 16 */     notNull(name, "name");
/* 17 */     return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:\"" + name + "\"}");
/*    */   }
/*    */   public static ItemStack fromUrl(Type type, String url) {
/* 20 */     ItemStack item = new ItemStack(type.mat, 1, (short)3);
/* 21 */     return withUrl(item, url);
/*    */   }
/*    */   public static ItemStack withUrl(ItemStack item, String url) {
/*    */     URI actualUrl;
/* 25 */     notNull(url, "url");
/*    */     
/*    */     try {
/* 28 */       actualUrl = new URI(url);
/* 29 */     } catch (URISyntaxException e) {
/* 30 */       throw new RuntimeException(e);
/*    */     } 
/* 32 */     String base64 = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
/* 33 */     base64 = Base64.getEncoder().encodeToString(base64.getBytes());
/* 34 */     return withBase64(item, base64);
/*    */   }
/*    */   public static ItemStack fromBase64(Type type, String base64) {
/* 37 */     ItemStack item = new ItemStack(type.mat, 1, (short)3);
/* 38 */     return withBase64(item, base64);
/*    */   }
/*    */   public static ItemStack withBase64(ItemStack item, String base64) {
/* 41 */     notNull(item, "item");
/* 42 */     notNull(base64, "base64");
/* 43 */     UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
/* 44 */     return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}");
/*    */   }
/*    */   
/*    */   public enum Type {
/* 48 */     BLOCK((String)Material.PLAYER_HEAD),
/* 49 */     ITEM((String)Material.PLAYER_HEAD);
/*    */     
/*    */     private Material mat;
/*    */ 
/*    */     
/*    */     Type(Material mat) {
/* 55 */       this.mat = mat;
/*    */     }
/*    */   }
/*    */   
/*    */   private static void notNull(Object o, String name) {
/* 60 */     if (o == null)
/* 61 */       throw new NullPointerException(name + " should not be null!"); 
/*    */   }
/*    */   
/*    */   public static ItemStack createSkullItem(String texture, String name, String... lore) {
/* 65 */     return (new SkullBuilder(texture))
/* 66 */       .name(name)
/* 67 */       .lore(lore)
/* 68 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\cor\\utils\items\SkullAPI.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package blizzard.development.rankup.utils.skulls;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Collectors;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import net.kyori.adventure.text.TextComponent;
/*    */ import net.kyori.adventure.text.format.TextColor;
/*    */ import net.kyori.adventure.text.format.TextDecoration;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class SkullBuilder {
/*    */   private final ItemStack item;
/*    */   
/*    */   public SkullBuilder(String base64) {
/* 20 */     this.item = new ItemStack(SkullAPI.fromBase64(SkullAPI.Type.ITEM, base64));
/*    */   }
/*    */   
/*    */   public SkullBuilder name(String name) {
/* 24 */     ItemMeta meta = this.item.getItemMeta();
/* 25 */     meta.displayName(parseGradient(name, false));
/* 26 */     this.item.setItemMeta(meta);
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public SkullBuilder lore(String... lore) {
/* 31 */     ItemMeta meta = this.item.getItemMeta();
/*    */ 
/*    */     
/* 34 */     List<Component> loreComponents = (List<Component>)Arrays.<String>stream(lore).map(line -> parseGradient(line, false)).collect(Collectors.toList());
/* 35 */     meta.lore(loreComponents);
/* 36 */     this.item.setItemMeta(meta);
/* 37 */     return this;
/*    */   }
/*    */   
/*    */   public ItemStack build() {
/* 41 */     return this.item;
/*    */   }
/*    */   private static Component parseGradient(String input, boolean bold) {
/*    */     Component component;
/* 45 */     Pattern pattern = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
/* 46 */     Matcher matcher = pattern.matcher(input);
/* 47 */     TextComponent textComponent = Component.empty();
/*    */     
/* 49 */     int lastIndex = 0;
/* 50 */     while (matcher.find()) {
/* 51 */       if (lastIndex < matcher.start()) {
/* 52 */         component = textComponent.append(((TextComponent)Component.text(input.substring(lastIndex, matcher.start()))
/* 53 */             .decoration(TextDecoration.BOLD, bold))
/* 54 */             .decoration(TextDecoration.ITALIC, false));
/*    */       }
/* 56 */       Color startColor = Color.decode("#" + matcher.group(1));
/* 57 */       String text = matcher.group(2);
/* 58 */       Color endColor = Color.decode("#" + matcher.group(3));
/* 59 */       component = component.append(applyGradient(text, startColor, endColor, bold));
/* 60 */       lastIndex = matcher.end();
/*    */     } 
/*    */     
/* 63 */     if (lastIndex < input.length()) {
/* 64 */       component = component.append(((TextComponent)Component.text(input.substring(lastIndex))
/* 65 */           .decoration(TextDecoration.BOLD, bold))
/* 66 */           .decoration(TextDecoration.ITALIC, false));
/*    */     }
/*    */     
/* 69 */     return component.decoration(TextDecoration.ITALIC, false);
/*    */   }
/*    */   private static Component applyGradient(String text, Color startColor, Color endColor, boolean bold) {
/*    */     Component component;
/* 73 */     int length = text.length();
/* 74 */     TextComponent textComponent = Component.empty();
/*    */     
/* 76 */     for (int i = 0; i < length; i++) {
/* 77 */       double ratio = i / (length - 1);
/* 78 */       int red = (int)(startColor.getRed() * (1.0D - ratio) + endColor.getRed() * ratio);
/* 79 */       int green = (int)(startColor.getGreen() * (1.0D - ratio) + endColor.getGreen() * ratio);
/* 80 */       int blue = (int)(startColor.getBlue() * (1.0D - ratio) + endColor.getBlue() * ratio);
/*    */       
/* 82 */       Color color = new Color(red, green, blue);
/* 83 */       TextColor textColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
/* 84 */       component = textComponent.append(((TextComponent)((TextComponent)Component.text(String.valueOf(text.charAt(i)))
/* 85 */           .color(textColor))
/* 86 */           .decoration(TextDecoration.BOLD, bold))
/* 87 */           .decoration(TextDecoration.ITALIC, false));
/*    */     } 
/* 89 */     return component;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\skulls\SkullBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
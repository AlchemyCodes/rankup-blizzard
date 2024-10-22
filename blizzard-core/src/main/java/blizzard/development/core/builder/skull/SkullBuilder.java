/*    */ package blizzard.development.core.builder.skull;
/*    */ 
/*    */ import blizzard.development.core.utils.items.SkullAPI;
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
/* 21 */     this.item = new ItemStack(SkullAPI.fromBase64(SkullAPI.Type.ITEM, base64));
/*    */   }
/*    */   
/*    */   public SkullBuilder name(String name) {
/* 25 */     ItemMeta meta = this.item.getItemMeta();
/* 26 */     meta.displayName(parseGradient(name, false));
/* 27 */     this.item.setItemMeta(meta);
/* 28 */     return this;
/*    */   }
/*    */   
/*    */   public SkullBuilder lore(String... lore) {
/* 32 */     ItemMeta meta = this.item.getItemMeta();
/*    */ 
/*    */     
/* 35 */     List<Component> loreComponents = (List<Component>)Arrays.<String>stream(lore).map(line -> parseGradient(line, false)).collect(Collectors.toList());
/* 36 */     meta.lore(loreComponents);
/* 37 */     this.item.setItemMeta(meta);
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   public ItemStack build() {
/* 42 */     return this.item;
/*    */   }
/*    */   private static Component parseGradient(String input, boolean bold) {
/*    */     Component component;
/* 46 */     Pattern pattern = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
/* 47 */     Matcher matcher = pattern.matcher(input);
/* 48 */     TextComponent textComponent = Component.empty();
/*    */     
/* 50 */     int lastIndex = 0;
/* 51 */     while (matcher.find()) {
/* 52 */       if (lastIndex < matcher.start()) {
/* 53 */         component = textComponent.append(((TextComponent)Component.text(input.substring(lastIndex, matcher.start()))
/* 54 */             .decoration(TextDecoration.BOLD, bold))
/* 55 */             .decoration(TextDecoration.ITALIC, false));
/*    */       }
/* 57 */       Color startColor = Color.decode("#" + matcher.group(1));
/* 58 */       String text = matcher.group(2);
/* 59 */       Color endColor = Color.decode("#" + matcher.group(3));
/* 60 */       component = component.append(applyGradient(text, startColor, endColor, bold));
/* 61 */       lastIndex = matcher.end();
/*    */     } 
/*    */     
/* 64 */     if (lastIndex < input.length()) {
/* 65 */       component = component.append(((TextComponent)Component.text(input.substring(lastIndex))
/* 66 */           .decoration(TextDecoration.BOLD, bold))
/* 67 */           .decoration(TextDecoration.ITALIC, false));
/*    */     }
/*    */     
/* 70 */     return component.decoration(TextDecoration.ITALIC, false);
/*    */   }
/*    */   private static Component applyGradient(String text, Color startColor, Color endColor, boolean bold) {
/*    */     Component component;
/* 74 */     int length = text.length();
/* 75 */     TextComponent textComponent = Component.empty();
/*    */     
/* 77 */     for (int i = 0; i < length; i++) {
/* 78 */       double ratio = i / (length - 1);
/* 79 */       int red = (int)(startColor.getRed() * (1.0D - ratio) + endColor.getRed() * ratio);
/* 80 */       int green = (int)(startColor.getGreen() * (1.0D - ratio) + endColor.getGreen() * ratio);
/* 81 */       int blue = (int)(startColor.getBlue() * (1.0D - ratio) + endColor.getBlue() * ratio);
/*    */       
/* 83 */       Color color = new Color(red, green, blue);
/* 84 */       TextColor textColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
/* 85 */       component = textComponent.append(((TextComponent)((TextComponent)Component.text(String.valueOf(text.charAt(i)))
/* 86 */           .color(textColor))
/* 87 */           .decoration(TextDecoration.BOLD, bold))
/* 88 */           .decoration(TextDecoration.ITALIC, false));
/*    */     } 
/* 90 */     return component;
/*    */   }
/*    */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-core-1.0-SNAPSHOT.jar!\blizzard\development\core\builder\skull\SkullBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
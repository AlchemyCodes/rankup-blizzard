/*     */ package blizzard.development.rankup.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.TextComponent;
/*     */ import net.kyori.adventure.text.format.TextColor;
/*     */ import net.kyori.adventure.text.format.TextDecoration;
/*     */ 
/*     */ public class TextUtil
/*     */ {
/*  13 */   private static final Pattern GRADIENT_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)<#([A-Fa-f0-9]{6})>");
/*  14 */   private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>(.*?)");
/*  15 */   private static final Pattern BOLD_PATTERN = Pattern.compile("<bold>(.*?)</bold>");
/*     */   public static Component parse(String input) {
/*     */     Component component;
/*  18 */     TextComponent textComponent = Component.empty();
/*  19 */     int lastEnd = 0;
/*     */     
/*  21 */     Matcher boldMatcher = BOLD_PATTERN.matcher(input);
/*  22 */     while (boldMatcher.find()) {
/*  23 */       if (lastEnd < boldMatcher.start()) {
/*  24 */         component = textComponent.append(parseColor(input.substring(lastEnd, boldMatcher.start())));
/*     */       }
/*  26 */       String boldText = boldMatcher.group(1);
/*  27 */       component = component.append(parseColor(boldText).decoration(TextDecoration.BOLD, true));
/*  28 */       lastEnd = boldMatcher.end();
/*     */     } 
/*     */     
/*  31 */     if (lastEnd < input.length()) {
/*  32 */       component = component.append(parseColor(input.substring(lastEnd)));
/*     */     }
/*     */     
/*  35 */     return component.decoration(TextDecoration.ITALIC, false);
/*     */   }
/*     */   private static Component parseColor(String input) {
/*     */     Component component;
/*  39 */     Matcher matcher = GRADIENT_PATTERN.matcher(input);
/*  40 */     TextComponent textComponent = Component.empty();
/*  41 */     int lastIndex = 0;
/*     */     
/*  43 */     while (matcher.find()) {
/*  44 */       if (lastIndex < matcher.start()) {
/*  45 */         component = textComponent.append(Component.text(input.substring(lastIndex, matcher.start()))
/*  46 */             .decoration(TextDecoration.ITALIC, false));
/*     */       }
/*     */       
/*  49 */       Color startColor = Color.decode("#" + matcher.group(1));
/*  50 */       String text = matcher.group(2);
/*  51 */       Color endColor = Color.decode("#" + matcher.group(3));
/*  52 */       component = component.append(applyGradient(text, startColor, endColor));
/*  53 */       lastIndex = matcher.end();
/*     */     } 
/*     */     
/*  56 */     if (lastIndex < input.length()) {
/*  57 */       component = component.append(parseHexColor(input.substring(lastIndex))
/*  58 */           .decoration(TextDecoration.ITALIC, false));
/*     */     }
/*     */     
/*  61 */     return component;
/*     */   }
/*     */   private static Component parseHexColor(String input) {
/*     */     Component component;
/*  65 */     Matcher matcher = HEX_COLOR_PATTERN.matcher(input);
/*  66 */     TextComponent textComponent = Component.empty();
/*  67 */     int lastIndex = 0;
/*     */     
/*  69 */     while (matcher.find()) {
/*  70 */       if (lastIndex < matcher.start()) {
/*  71 */         component = textComponent.append(Component.text(input.substring(lastIndex, matcher.start()))
/*  72 */             .decoration(TextDecoration.ITALIC, false));
/*     */       }
/*     */       
/*  75 */       String colorHex = matcher.group(1);
/*  76 */       TextColor color = TextColor.fromHexString("#" + colorHex);
/*  77 */       String text = matcher.group(2);
/*     */       
/*  79 */       component = component.append(((TextComponent)Component.text(text)
/*  80 */           .color(color))
/*  81 */           .decoration(TextDecoration.ITALIC, false));
/*  82 */       lastIndex = matcher.end();
/*     */     } 
/*     */     
/*  85 */     if (lastIndex < input.length()) {
/*  86 */       component = component.append(Component.text(input.substring(lastIndex))
/*  87 */           .decoration(TextDecoration.ITALIC, false));
/*     */     }
/*     */     
/*  90 */     return component;
/*     */   }
/*     */   private static Component applyGradient(String text, Color startColor, Color endColor) {
/*     */     Component component;
/*  94 */     int length = text.length();
/*  95 */     TextComponent textComponent = Component.empty();
/*     */     
/*  97 */     for (int i = 0; i < length; i++) {
/*  98 */       double ratio = i / (length - 1);
/*  99 */       int red = (int)(startColor.getRed() * (1.0D - ratio) + endColor.getRed() * ratio);
/* 100 */       int green = (int)(startColor.getGreen() * (1.0D - ratio) + endColor.getGreen() * ratio);
/* 101 */       int blue = (int)(startColor.getBlue() * (1.0D - ratio) + endColor.getBlue() * ratio);
/*     */       
/* 103 */       Color color = new Color(red, green, blue);
/* 104 */       TextColor textColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
/* 105 */       component = textComponent.append(((TextComponent)Component.text(String.valueOf(text.charAt(i)))
/* 106 */           .color(textColor))
/* 107 */           .decoration(TextDecoration.ITALIC, false));
/*     */     } 
/* 109 */     return component;
/*     */   }
/*     */ }


/* Location:              C:\Users\joaop\Desktop\blizzard-rankup-1.0-SNAPSHOT.jar!\blizzard\development\ranku\\utils\TextUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
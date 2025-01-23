package blizzard.development.currencies.enums;

public enum Currencies {
    COINS("coins", "§2[$]"),
    SOULS("souls", "§d[👻]"),
    FLAKES("flakes", "§b[❄]"),
    FOSSILS("fossils", "§f[🦴]"),
    SPAWNERSLIMIT("spawners_limits", "§e[∞]"),
    BLOCKS("blocks", "§b[❒]");
    
    private final String name, emoji;

    Currencies(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }
}

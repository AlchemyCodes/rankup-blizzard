package blizzard.development.currencies.enums;

public enum Currencies {
    COINS("coins", "§7[§2$§7]"),
    SOULS("souls", "§7[§d👻§7]"),
    FLAKES("flakes", "§7[§b❄§7]"),
    FOSSILS("fossils", "§7[§f🦴§7]");

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

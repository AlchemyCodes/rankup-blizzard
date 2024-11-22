package blizzard.development.currencies.enums;

public enum Currencies {
    COINS("coins", "$"),
    SOULS("souls", "👻"),
    FLAKES("flakes", "❄"),
    FOSSILS("fossils", "🦴");

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

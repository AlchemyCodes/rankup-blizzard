package blizzard.development.clans.enums;

public enum Roles {
    LEADER(
            "Líder",
            "⭐",
            4
    ),
    CAPTAIN(
            "Capitão",
            "⚓",
            3
    ),
    RELIABLE(
            "Confiável",
            "🛡",
            2
    ),
    MEMBER(
            "Membro",
            "⚔",
            1
    );

    private final String name, emoji;
    private final int priority;

    Roles(String name, String emoji, int priority) {
        this.name = name;
        this.emoji = emoji;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getPriority() {
        return priority;
    }

    public static Roles fromName(String name) {
        for (Roles role : values()) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }
}

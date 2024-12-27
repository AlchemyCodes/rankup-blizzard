package blizzard.development.spawners.handlers.enums.spawners;

public enum Enchantments {
    SPEED("speed"),
    LUCKY("lucky"),
    EXPERIENCE("experience");

    private final String name;

    Enchantments(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

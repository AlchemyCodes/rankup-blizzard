package blizzard.development.mine.mine.enums;

public enum LocationEnum {
    SPAWN("spawn"),
    EXIT("exit"),
    CENTER("center"),
    DISPLAY("display"),
    EXTRACTOR_NPC("extractor_npc"),
    NPC("npc");

    private final String name;

    LocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

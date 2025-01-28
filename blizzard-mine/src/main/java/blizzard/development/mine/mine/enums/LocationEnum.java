package blizzard.development.mine.mine.enums;

public enum LocationEnum {
    SPAWN("spawn"),
    EXIT("exit"),
    CENTER("center"),
    DISPLAY("display"),
    EXTRACTOR_NPC("extractor_npc"),
    NPC("npc"),
    TOP_ONE_NPC("top_one_npc"),
    TOP_TWO_NPC("top_two_npc"),
    TOP_TREE_NPC("top_tree_npc");

    private final String name;

    LocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

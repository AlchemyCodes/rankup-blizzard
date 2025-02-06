package blizzard.development.vips.enums;

public enum VipEnum {
    ALCHEMY("alchemy"),
    BLIZZARD("blizzard"),
    DIAMOND("esmeralda"),
    EMERALD("diamante"),
    GOLD("ouro");

    private final String name;

    VipEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

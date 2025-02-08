package blizzard.development.mine.mine.enums;

public enum VipEnum {
    ALCHEMY("alchemy", 1.2),
    BLIZZARD("blizzard", 1.2),
    DIAMOND("esmeralda", 1.2),
    EMERALD("diamante", 1.2),
    GOLD("ouro", 1.2);

    private final String permission;
    private final Double bonus;

    VipEnum(String permission, Double bonus) {
        this.permission = permission;
        this.bonus = bonus;
    }

    public String getPermission() {
        return permission;
    }

    public Double getBonus() {
        return bonus;
    }
}

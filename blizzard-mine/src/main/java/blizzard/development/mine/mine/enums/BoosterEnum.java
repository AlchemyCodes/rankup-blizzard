package blizzard.development.mine.mine.enums;

public enum BoosterEnum {

    BOOSTER1("Booster1", 1.2, 60),
    BOOSTER2("Booster2", 1.5, 60),
    BOOSTER3("Booster3", 1.8, 60);

    private final String name;
    private final Double multiplier;
    private final Integer duration;

    BoosterEnum(String name, Double multiplier, Integer duration) {
        this.name = name;
        this.multiplier = multiplier;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public Integer getDuration() {
        return duration;
    }
}

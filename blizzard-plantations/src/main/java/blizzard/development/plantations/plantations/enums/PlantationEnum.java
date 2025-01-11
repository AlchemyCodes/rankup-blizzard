package blizzard.development.plantations.plantations.enums;

public enum PlantationEnum {

    POTATO("POTATOES", 0),
    CARROT("CARROTS", 1),
    TOMATO("BEETROOTS", 2),
    CORN("WHEAT", 3);

    private final String name;
    private final int level;

    PlantationEnum(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public static PlantationEnum getByName(String name) {
        for (PlantationEnum plantation : values()) {
            if (plantation.getName().equals(name)) {
                return plantation;
            }
        }
        return POTATO;
    }
}

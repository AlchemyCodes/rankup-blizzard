package blizzard.development.plantations.plantations.enums;

public enum PlantationEnum {

    POTATO("POTATOES"),
    CARROT("CARROTS"),
    CORN("WHEAT"),
    TOMATO("BEETROOTS");

    private final String name;

    PlantationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

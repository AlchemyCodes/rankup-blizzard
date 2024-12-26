package blizzard.development.plantations.plantations.enums;

public enum ToolsEnum {

    TOOL("cultivadora");

    private final String type;

    ToolsEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

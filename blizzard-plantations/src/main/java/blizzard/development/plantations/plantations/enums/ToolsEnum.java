package blizzard.development.plantations.plantations.enums;

public enum ToolsEnum {

    PLOWING("arar"),
    TOOL("cultivo");

    private final String type;

    ToolsEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

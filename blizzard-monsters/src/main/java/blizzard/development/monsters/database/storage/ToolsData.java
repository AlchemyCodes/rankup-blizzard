package blizzard.development.monsters.database.storage;

import lombok.Data;

public @Data class ToolsData {
    private String id, type, damage, owner;

    public ToolsData(String id, String type, String damage, String owner) {
        this.id = id;
        this.type = type;
        this.damage = damage;
        this.owner = owner;
    }
}

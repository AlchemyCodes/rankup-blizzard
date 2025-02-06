package blizzard.development.monsters.database.storage;

import lombok.Data;

public @Data class ToolsData {
    private String id, type, skin, owner;
    private Integer damage, experienced;

    public ToolsData(String id, String type, String skin, Integer damage, Integer experienced, String owner) {
        this.id = id;
        this.type = type;
        this.skin = skin;
        this.damage = damage;
        this.experienced = experienced;
        this.owner = owner;
    }
}

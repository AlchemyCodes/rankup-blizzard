package blizzard.development.monsters.database.storage;

import lombok.Data;

public @Data class MonstersData {
    private String id, type, location, owner;
    private Integer life;

    public MonstersData(String id, String type, String location, Integer life, String owner) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.life = life;
        this.owner = owner;
    }
}

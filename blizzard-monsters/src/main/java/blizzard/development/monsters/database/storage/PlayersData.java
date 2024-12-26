package blizzard.development.monsters.database.storage;

import lombok.Data;

public @Data class PlayersData {
    private String uuid, nickname;

    public PlayersData(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }
}

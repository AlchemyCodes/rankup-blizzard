package blizzard.development.monsters.database.storage;

import lombok.Data;

public @Data class PlayersData {
    private String uuid, nickname;
    private Integer monstersLimit;

    public PlayersData(String uuid, String nickname, Integer monstersLimit) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.monstersLimit = monstersLimit;
    }
}

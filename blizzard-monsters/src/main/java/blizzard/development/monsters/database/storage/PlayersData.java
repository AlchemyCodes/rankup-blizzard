package blizzard.development.monsters.database.storage;

import lombok.Data;

import java.util.List;

public @Data class PlayersData {
    private String uuid, nickname;
    private Integer monstersLimit;
    private List<String> rewards;

    public PlayersData(String uuid, String nickname, Integer monstersLimit, List<String> rewards) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.monstersLimit = monstersLimit;
        this.rewards = rewards;
    }
}

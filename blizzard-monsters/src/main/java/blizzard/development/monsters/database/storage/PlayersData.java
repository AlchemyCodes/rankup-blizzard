package blizzard.development.monsters.database.storage;

import lombok.Data;

import java.util.List;

public @Data class PlayersData {
    private String uuid, nickname;
    private Integer killedMonsters, monstersLimit;
    private List<String> rewards;

    public PlayersData(String uuid, String nickname, Integer killedMonsters, Integer monstersLimit, List<String> rewards) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.killedMonsters = killedMonsters;
        this.monstersLimit = monstersLimit;
        this.rewards = rewards;
    }
}

package blizzard.development.time.database.storage;

public class PlayersData {
    private String uuid, nickname;
    private long playTime;

    public PlayersData(String uuid, String nickname, long playTime) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.playTime = playTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }
}

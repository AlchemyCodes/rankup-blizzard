package blizzard.development.currencies.database.storage;

public class PlayersData {
    private String uuid, nickname;
    private Double souls;

    public PlayersData(String uuid, String nickname, Double souls) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.souls = souls;
    }

    // Commons

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

    // Currencies

    public Double getSouls() {
        return souls;
    }

    public void setSouls(Double souls) {
        this.souls = souls;
    }
}

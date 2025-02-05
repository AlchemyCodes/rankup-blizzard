package blizzard.development.mine.database.storage;

public class BoosterData {
    private String uuid, nickname;
    private String boosterName;
    private int boosterDuration;

    public BoosterData(String uuid, String nickname, String boosterName, int boosterDuration) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.boosterName = boosterName;
        this.boosterDuration = boosterDuration;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBoosterName() {
        return boosterName;
    }

    public void setBoosterName(String boosterName) {
        this.boosterName = boosterName;
    }

    public int getBoosterDuration() {
        return boosterDuration;
    }

    public void setBoosterDuration(int boosterDuration) {
        this.boosterDuration = boosterDuration;
    }

    public String getUuid() {
        return uuid;
    }

}

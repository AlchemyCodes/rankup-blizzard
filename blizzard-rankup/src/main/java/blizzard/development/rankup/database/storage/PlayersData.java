package blizzard.development.rankup.database.storage;

public class PlayersData {
    private String uuid;
    private String nickname;
    private String rank;
    private int prestige;

    public PlayersData(String uuid, String nickname, String rank, int prestige) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.rank = rank;
        this.prestige = prestige;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }
}

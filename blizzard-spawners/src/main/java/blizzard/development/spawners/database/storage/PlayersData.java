package blizzard.development.spawners.database.storage;

public class PlayersData {
    private String uuid, nickname;
    private Double purchasedSpawners, killedMobs;

    public PlayersData(String uuid, String nickname, Double purchasedSpawners, Double killedMobs) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.purchasedSpawners = purchasedSpawners;
        this.killedMobs = killedMobs;
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

    public Double getPurchasedSpawners() {
        return purchasedSpawners;
    }

    public void setPurchasedSpawners(Double purchasedSpawners) {
        this.purchasedSpawners = purchasedSpawners;
    }

    public Double getKilledMobs() {
        return killedMobs;
    }

    public void setKilledMobs(Double killedMobs) {
        this.killedMobs = killedMobs;
    }
}

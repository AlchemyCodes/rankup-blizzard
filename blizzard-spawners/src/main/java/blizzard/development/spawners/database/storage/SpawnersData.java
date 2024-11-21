package blizzard.development.spawners.database.storage;

public class SpawnersData {
    private String id, type, location, mob_location, nickname, plotId;
    private Double amount;

    public SpawnersData(String id, String type, Double amount, String location, String mobLocation, String nickname, String plotId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.location = location;
        mob_location = mobLocation;
        this.nickname = nickname;
        this.plotId = plotId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public String getMob_location() {
        return mob_location;
    }

    public void setMob_location(String mob_location) {
        this.mob_location = mob_location;
    }
}

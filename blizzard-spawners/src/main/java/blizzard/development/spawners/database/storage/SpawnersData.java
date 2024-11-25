package blizzard.development.spawners.database.storage;

public class SpawnersData {
    private String id;
    private String type;
    private String location;
    private String mobLocation;
    private String nickname;
    private String plotId;
    private Double amount, mobAmount;
    private Integer speedLevel, luckyLevel, experienceLevel;

    public SpawnersData(String id, String type, String location, String mobLocation, String nickname, String plotId, Double amount, Double mobAmount, Integer speedLevel, Integer luckyLevel, Integer experienceLevel) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.mobLocation = mobLocation;
        this.nickname = nickname;
        this.plotId = plotId;
        this.amount = amount;
        this.mobAmount = mobAmount;
        this.speedLevel = speedLevel;
        this.luckyLevel = luckyLevel;
        this.experienceLevel = experienceLevel;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobLocation() {
        return mobLocation;
    }

    public void setMobLocation(String mobLocation) {
        this.mobLocation = mobLocation;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMobAmount() {
        return mobAmount;
    }

    public void setMobAmount(Double mobAmount) {
        this.mobAmount = mobAmount;
    }

    public Integer getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(Integer speedLevel) {
        this.speedLevel = speedLevel;
    }

    public Integer getLuckyLevel() {
        return luckyLevel;
    }

    public void setLuckyLevel(Integer luckyLevel) {
        this.luckyLevel = luckyLevel;
    }

    public Integer getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(Integer experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}

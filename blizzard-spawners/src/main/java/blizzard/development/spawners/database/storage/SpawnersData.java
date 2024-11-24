package blizzard.development.spawners.database.storage;

public class SpawnersData {
    private String id;
    private String type;
    private String location;
    private String mob_location;
    private String nickname;
    private String plotId;
    private Double amount, mob_amount;
    private Integer speed_level, lucky_level, experience_level;

    public SpawnersData(String id, String type, Double amount, Double mob_amount, String location, String mobLocation, String nickname, String plotId, Integer speedLevel, Integer luckyLevel, Integer experienceLevel) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.mob_amount = mob_amount;
        this.location = location;
        mob_location = mobLocation;
        this.nickname = nickname;
        this.plotId = plotId;
        speed_level = speedLevel;
        lucky_level = luckyLevel;
        experience_level = experienceLevel;
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

    public Double getMob_amount() {
        return mob_amount;
    }

    public void setMob_amount(Double mob_amount) {
        this.mob_amount = mob_amount;
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

    public Integer getSpeed_level() {
        return speed_level;
    }

    public void setSpeed_level(Integer speed_level) {
        this.speed_level = speed_level;
    }

    public Integer getLucky_level() {
        return lucky_level;
    }

    public void setLucky_level(Integer lucky_level) {
        this.lucky_level = lucky_level;
    }

    public Integer getExperience_level() {
        return experience_level;
    }

    public void setExperience_level(Integer experience_level) {
        this.experience_level = experience_level;
    }
}

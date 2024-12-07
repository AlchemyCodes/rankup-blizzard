package blizzard.development.spawners.database.storage;

import java.util.List;

public class SpawnersData {
    private String id;
    private String type;
    private String location;
    private String mobLocation;
    private String nickname;
    private String state;
    private String plotId;
    private Double amount, mobAmount, drops;
    private Integer speedLevel, luckyLevel, experienceLevel;
    private List<String> friends;
    private Integer friendsLimit;

    public SpawnersData(String id, String type, String location, String mobLocation, String nickname, String state, String plotId, Double amount, Double mobAmount, Double drops, Integer speedLevel, Integer luckyLevel, Integer experienceLevel, List<String> friends, Integer friendsLimit) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.mobLocation = mobLocation;
        this.nickname = nickname;
        this.state = state;
        this.plotId = plotId;
        this.amount = amount;
        this.mobAmount = mobAmount;
        this.drops = drops;
        this.speedLevel = speedLevel;
        this.luckyLevel = luckyLevel;
        this.experienceLevel = experienceLevel;
        this.friends = friends;
        this.friendsLimit = friendsLimit;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Double getDrops() {
        return drops;
    }

    public void setDrops(Double drops) {
        this.drops = drops;
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Integer getFriendsLimit() {
        return friendsLimit;
    }

    public void setFriendsLimit(Integer friendsLimit) {
        this.friendsLimit = friendsLimit;
    }
}

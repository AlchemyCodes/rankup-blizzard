package blizzard.development.spawners.database.storage;

import java.util.List;

public class SlaughterhouseData {
    private String id;
    private String tier;
    private String location;
    private String nickname;
    private String state;
    private String plotId;
    private List<String> friends;
    private Integer friendsLimit;

    public SlaughterhouseData(String id, String tier, String location, String nickname, String state, String plotId, List<String> friends, Integer friendsLimit) {
        this.id = id;
        this.tier = tier;
        this.location = location;
        this.nickname = nickname;
        this.state = state;
        this.plotId = plotId;
        this.friends = friends;
        this.friendsLimit = friendsLimit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
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

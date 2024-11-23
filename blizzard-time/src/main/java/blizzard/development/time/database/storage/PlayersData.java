package blizzard.development.time.database.storage;

import java.util.List;

public class PlayersData {
    private String uuid, nickname;
    private long playTime;
    private List<String> completedMissions, notifiedMissions;

    public PlayersData(String uuid, String nickname, long playTime, List<String> completedMissions, List<String> notifiedMissions) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.playTime = playTime;
        this.completedMissions = completedMissions;
        this.notifiedMissions = notifiedMissions;
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

    public List<String> getCompletedMissions() {
        return completedMissions;
    }

    public void setCompletedMissions(List<String> completedMissions) {
        this.completedMissions = completedMissions;
    }

    public List<String> getNotifiedMissions() {
        return notifiedMissions;
    }

    public void setNotifiedMissions(List<String> notifiedMissions) {
        this.notifiedMissions = notifiedMissions;
    }
}

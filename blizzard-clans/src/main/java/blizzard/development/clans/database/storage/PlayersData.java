package blizzard.development.clans.database.storage;

public class PlayersData {
    private String uuid, nickname, clan, role, invites;
    private int kills, deaths;

    public PlayersData(String uuid, String nickname, String clan, String role, String invites, int kills, int deaths) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.clan = clan;
        this.role = role;
        this.invites = invites;
        this.kills = kills;
        this.deaths = deaths;
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

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInvites() {
        return invites;
    }

    public void setInvites(String invites) {
        this.invites = invites;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}

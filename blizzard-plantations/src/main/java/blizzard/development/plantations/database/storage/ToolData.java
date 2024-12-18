package blizzard.development.plantations.database.storage;

public class ToolData {

    private String id, type, nickname;
    private Integer blocks, botany, agility, explosion, thunderstorm, xray;

    public ToolData(String id, String type, String nickname, Integer blocks, Integer botany, Integer agility, Integer explosion, Integer thunderstorm, Integer xray) {
        this.id = id;
        this.type = type;
        this.nickname = nickname;
        this.blocks = blocks;
        this.botany = botany;
        this.agility = agility;
        this.explosion = explosion;
        this.thunderstorm = thunderstorm;
        this.xray = xray;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getBotany() {
        return botany;
    }

    public void setBotany(Integer botany) {
        this.botany = botany;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getExplosion() {
        return explosion;
    }

    public void setExplosion(Integer explosion) {
        this.explosion = explosion;
    }

    public Integer getThunderstorm() {
        return thunderstorm;
    }

    public void setThunderstorm(Integer thunderstorm) {
        this.thunderstorm = thunderstorm;
    }

    public Integer getXray() {
        return xray;
    }

    public void setXray(Integer xray) {
        this.xray = xray;
    }
}

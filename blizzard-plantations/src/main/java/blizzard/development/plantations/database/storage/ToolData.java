package blizzard.development.plantations.database.storage;

public class ToolData {

    private String id, type, nickname;
    private Integer botany, agility, explosion, accelerator, plow;

    public ToolData(String id, String type, String nickname, Integer botany, Integer agility, Integer explosion, Integer accelerator, Integer plow) {
        this.id = id;
        this.type = type;
        this.nickname = nickname;
        this.botany = botany;
        this.agility = agility;
        this.explosion = explosion;
        this.accelerator = accelerator;
        this.plow = plow;
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

    public Integer getAccelerator() {
        return accelerator;
    }

    public void setAccelerator(Integer accelerator) {
        this.accelerator = accelerator;
    }

    public Integer getPlow() {
        return plow;
    }

    public void setPlow(Integer plow) {
        this.plow = plow;
    }
}

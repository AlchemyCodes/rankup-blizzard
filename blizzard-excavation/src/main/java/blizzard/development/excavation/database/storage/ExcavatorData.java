package blizzard.development.excavation.database.storage;

public class ExcavatorData {

    private String nickname;
    private Integer efficiency;
    private Integer agility;
    private Integer extractor;

    public ExcavatorData(String nickname, Integer efficiency, Integer agility, Integer extractor) {
        this.nickname = nickname;
        this.efficiency = efficiency;
        this.agility = agility;
        this.extractor = extractor;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getExtractor() {
        return extractor;
    }

    public void setExtractor(Integer extractor) {
        this.extractor = extractor;
    }
}

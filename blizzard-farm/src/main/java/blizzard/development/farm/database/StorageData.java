package blizzard.development.farm.database;

public class StorageData {

    private String uuid;
    private String nickname;
    private String plotId;
    private int carrotStored;
    private int potatoStored;
    private int wheatStored;
    private int melonStored;
    private int cactusStored;


    public StorageData(String uuid, String nickname, String plotId, int carrotStored, int potatoStored, int wheatStored, int melonStored, int cactusStored) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.plotId = plotId;
        this.carrotStored = carrotStored;
        this.potatoStored = potatoStored;
        this.wheatStored = wheatStored;
        this.melonStored = melonStored;
        this.cactusStored = cactusStored;
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

    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public int getCarrotStored() {
        return carrotStored;
    }

    public void setCarrotStored(int carrotStored) {
        this.carrotStored = carrotStored;
    }

    public int getPotatoStored() {
        return potatoStored;
    }

    public void setPotatoStored(int potatoStored) {
        this.potatoStored = potatoStored;
    }

    public int getWheatStored() {
        return wheatStored;
    }

    public void setWheatStored(int wheatStored) {
        this.wheatStored = wheatStored;
    }

    public int getMelonStored() {
        return melonStored;
    }

    public void setMelonStored(int melonStored) {
        this.melonStored = melonStored;
    }

    public int getCactusStored() {
        return cactusStored;
    }

    public void setCactusStored(int cactusStored) {
        this.cactusStored = cactusStored;
    }
}

package blizzard.development.clans.database.storage;

public class ClansData {
    private String clan;
    private String owner;
    private String tag;
    private String name;
    private String members;
    private int max;
    private long money;
    private boolean friendlyfire;
    private double kdr;
    private String allies;
    private String base;
    private String creationDate;

    public ClansData(String clan, String owner, String tag, String name, String members, int max, long money, boolean friendlyfire, double kdr, String allies, String base, String creationDate) {
        this.clan = clan;
        this.owner = owner;
        this.tag = tag;
        this.name = name;
        this.members = members;
        this.max = max;
        this.money = money;
        this.friendlyfire = friendlyfire;
        this.kdr = kdr;
        this.allies = allies;
        this.base = base;
        this.creationDate = creationDate;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public boolean isFriendlyfire() {
        return friendlyfire;
    }

    public void setFriendlyfire(boolean friendlyfire) {
        this.friendlyfire = friendlyfire;
    }

    public double getKdr() {
        return kdr;
    }

    public void setKdr(double kdr) {
        this.kdr = kdr;
    }

    public String getAllies() {
        return allies;
    }

    public void setAllies(String allies) {
        this.allies = allies;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}

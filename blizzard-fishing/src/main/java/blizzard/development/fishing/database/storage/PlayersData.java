package blizzard.development.fishing.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersData {
    private String uuid;
    private String nickname;
    private int tilapia;
    private int bacalhau;
    private int salmao;
    private int atum;
    private int baiacu;
    private int sardinha;
    private int linguado;
    private int pirarucu;

    public PlayersData(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }
}

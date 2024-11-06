package blizzard.development.fishing.database.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersData {
    private String uuid, nickname;
    private int bacalhau;
    private int salmao;
    private int caranguejo;
    private int lagosta;
    private int lula;
    private int lula_brilhante;
    private int tubarao;
    private int baleia;
    private int storage;
    private int trash;

    public PlayersData(String uuid, String nickname, int bacalhau, int salmao, int caranguejo,
                       int lagosta, int lula, int lula_brilhante, int tubarao, int baleia, int storage, int trash) {

        this.uuid = uuid;
        this.nickname = nickname;
        this.bacalhau = bacalhau;
        this.salmao = salmao;
        this.caranguejo = caranguejo;
        this.lagosta = lagosta;
        this.lula = lula;
        this.lula_brilhante = lula_brilhante;
        this.tubarao = tubarao;
        this.baleia = baleia;
        this.storage = storage;
        this.trash = trash;
    }

    public int capacity() {
        return bacalhau + salmao + caranguejo + lagosta + lula + lula_brilhante + tubarao + baleia;
        }
    }


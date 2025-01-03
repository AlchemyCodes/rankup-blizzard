package blizzard.development.fishing.database.storage;

import blizzard.development.fishing.enums.RodMaterials;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RodsData {
    private String uuid, nickname;
    private int strength;
    private double xp;
    private int specialist; // fará uma chuva de diamantes e irá dar mais peixes e xp
    private int experienced; // fará com que o player consiga mais xp
    private int lucky; // fará com que o player tenha chance de conseguir mais de 1 peixe de uma vez
    private List<RodMaterials> rodMaterials;

    public RodsData(String uuid, String nickname, int strength, double xp, int specialist, int experienced, int lucky,
                    List<RodMaterials> rodMaterials) {

        this.uuid = uuid;
        this.nickname = nickname;
        this.strength = strength;
        this.xp = xp;
        this.specialist = specialist;
        this.experienced = experienced;
        this.lucky = lucky;
        this.rodMaterials = rodMaterials;
    }
    }
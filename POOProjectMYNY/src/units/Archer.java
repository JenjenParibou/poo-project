package units;

public class Archer extends Unit {
    public Archer() {
        this.hp = 80;
        this.atk = 30;
        this.def = 10;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 3; //range is how many tiles the enemy has to be away to be attacked
        this.cost = 50; //this'll be changed according to yudas's work on economy
        this.type = "Archer";
    }

}

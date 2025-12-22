package units;

public class Soldat extends Unit {
    public Soldat() {
        this.hp = 100;
        this.atk = 30;
        this.def = 20;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 1; //range is how many tiles the enemy has to be away to be attacked
        this.cost = 50; //this'll be changed according to yudas's work on economy
        this.type = "Soldat";
    }

}

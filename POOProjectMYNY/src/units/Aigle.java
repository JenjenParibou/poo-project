package units;

public class Aigle extends Unit {
    public Aigle() {
        this.hp = 65;
        this.atk = 25;
        this.def = 15;
        this.spd = 2; //speed is how many tiles of the map a unit can move per turn
        this.range = 1; //range is how many tiles the enemy has to be away to be attacked
        this.cost = 80; //this'll be changed according to yudas's work on economy
        this.type = "Aigle";
        this.aerial = true; //aerial troops can fly over water
    }

}
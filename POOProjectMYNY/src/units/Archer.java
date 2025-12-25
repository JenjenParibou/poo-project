package units;

import main.ConsoleColors;

public class Archer extends Unit {
    public Archer() {
    	this.icon = "A";
        this.hp = 70;
        this.atk = 25;
        this.def = 20;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 3; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Archer";
        this.aerial = false;
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
        this.icon = icon +ConsoleColors.colorText(String.format("%02d", id), ConsoleColors.PURPLE) ; 
    }

}

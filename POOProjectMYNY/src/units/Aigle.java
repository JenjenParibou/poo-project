package units;

import main.ConsoleColors;

public class Aigle extends Unit {
    public Aigle() {
    	this.icon = "E";
        this.hp = 65;
        this.atk = 25;
        this.def = 15;
        this.spd = 2; //speed is how many tiles of the map a unit can move per turn
        this.range = 1; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Aigle";
        this.aerial = true; //aerial troops can fly over water
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
        this.icon = icon +ConsoleColors.colorText(String.format("%02d", id), ConsoleColors.PURPLE) ; 
    }

}
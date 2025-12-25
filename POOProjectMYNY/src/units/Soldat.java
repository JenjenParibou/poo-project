package units;

import main.ConsoleColors;

public class Soldat extends Unit {
    public Soldat() {
    	this.icon = "S";
        this.hp = 100;
        this.atk = 30;
        this.def = 20;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 1; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Soldat";
        this.aerial = false;
        this.cost.put("Gold", 20);
        this.cost.put("Food", 20);
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
        this.icon = icon +ConsoleColors.colorText(String.format("%02d", id), ConsoleColors.PURPLE) ; 
    }

}

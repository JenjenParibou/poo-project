package units;



public class Fish extends Unit {
    public Fish() {
    	this.icon = "F";
    	this.basehp = 30;
        this.hp = basehp;
        this.atk = 25;
        this.def = 10;
        this.spd = 0; //speed is how many tiles of the map a unit can move per turn
        this.range = 4; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Fish";
        this.elementType = "Unit";
        this.aerial = true;//aerial troops can fly over water
        this.marine = true; 
        this.cost.put("Gold", 50);
        this.cost.put("Food", 50);
        this.cost.put("Stone", 0);
        this.cost.put("Wood", 10);
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
    }


}

package units;



public class Archer extends Unit {
    public Archer() {
    	this.icon = "A";
        this.basehp = 40;
        this.hp = basehp;
        this.atk = 25;
        this.def = 20;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 3; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Archer";
        this.elementType = "Unit";
        this.aerial = false;
        this.cost.put("Gold", 50);
        this.cost.put("Food", 30);
        this.cost.put("Wood", 70);
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
    }

}

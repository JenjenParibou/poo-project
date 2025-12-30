package units;



public class Giant extends Unit {
    public Giant() {
    	this.icon = "G";
        this.basehp = 120;
        this.hp = basehp;
        this.atk = 40;
        this.def = 25;
        this.spd = 1; //speed is how many tiles of the map a unit can move per turn
        this.range = 1; //range is how many tiles the enemy has to be away to be attacked
        this.type = "Giant";
        this.elementType = "Unit";
        this.aerial = false;
        this.marine = false;
        this.cost.put("Gold", 40);
        this.cost.put("Food", 100);
        this.cost.put("Stone", 10);
        this.cost.put("Wood", 50);
        id = numOfUnits;
        numOfUnits++;
        if(numOfUnits>99) {numOfUnits=0;}
    }
}
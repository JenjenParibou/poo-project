package units;
import main.Game;
import main.Point;
import main.ConsoleColors;
import map.Element;
import map.*;
import batiment.*;
import java.util.HashMap;

public abstract class Unit extends Element{
    // stats of each unit
    public int atk, def, spd, range,  id, faction, basehp;
    public boolean aerial;
    public String type = "Unit";
    static int numOfUnits;
    public HashMap<String, Integer> cost= new HashMap<>();// units can need multiple ressources in order to be deployed
	public String elementType = "Building";
    

    public boolean isAerial() {
        return aerial;
    }

    public void setAerial(boolean aerial) {
        this.aerial = aerial;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public void showStats() {
    	System.out.println(ConsoleColors.GREEN + "Health: " + ConsoleColors.RESET + hp);
    	System.out.println(ConsoleColors.GREEN + "Attack: " + ConsoleColors.RESET + atk);
    	System.out.println(ConsoleColors.GREEN + "Defense: " + ConsoleColors.RESET + def);
    	System.out.println(ConsoleColors.GREEN + "Speed: " + ConsoleColors.RESET + spd);
    	System.out.println(ConsoleColors.GREEN + "Range: " + ConsoleColors.RESET + range);
    }
    
    public void improveStats() {
    	int improveRatio = 10;
    	if(Game.gameLevel < 10) {
    		improveRatio = Game.gameLevel;
    	}   	
    	basehp = basehp + 10*improveRatio;
    	hp = basehp;
    	atk += (int) 2.5*improveRatio;
    	def += (int) 2.5*improveRatio;
    }
    
    public void getCost() {
   	 for (String i: cost.keySet()) {
   	        System.out.println(ConsoleColors.GREEN + i  + " cost: " +ConsoleColors.RESET+ cost.get(i));
   	    }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public int Attacking(Unit cible) {
        // dont forget to add a function that checks if the target is in range before
        // calling this method
        // check if the unit can attack aerial/ground units
        if (cible.isAerial() && !this.isAerial()) {
            return 0; // Ground units cannot attack aerial units
        }
        // no need to check if the target or the attacker is alive because if they are
        // dead they will be deleted from the game

        // i added a random factor to the attacking that makes it so you deal between
        // 80% and 120% of your attack stat
        int damage = (int) (this.atk * (0.8 + Math.random() * 0.4)) - cible.getDef();
        if (damage < 0) {
            damage = 0; // If defense of the target is higher than attack, no damage is dealt
        }
        cible.setHp(cible.getHp() - damage);
        if(cible.faction == 1) {
        	Game.updateUnit(cible, Game.playerUnits);
        } else
        	Game.updateUnit(cible, Game.enemyUnits);
        
        return damage;
    }
    
    
//    public Point checkForElement(String typeToLookFor){//Checks around unit if there are enemy units to attack
//    	Point p = new Point();
//    	int leftmost = x - range;
//    	int rightmost = x + range;
//    	int upmost = y - range;
//    	int downmost = y + range;
//    	
//    	for (int i = upmost; i<=downmost; i++) {
//    		for (int j = leftmost; j<=rightmost; j++) {
//    			if ()
//    		}
//    	}
//    	return p;
//    }
    
    
    public int Attacking(Batiment cible) {

        int damage = (int) (this.atk);
        if (damage < 0) {
            damage = 0;
        }
        cible.hp -= damage;
        Game.updateBuilding(cible, Game.gameBuildings);
        
        return damage;
    }
    
    
    public boolean moveTo(int x, int y) {
    	if(!Game.canAddElem(this.x + x, this.y+y, aerial, this.faction)) {System.out.println("Couldn't move unit.");return false;}
    	Map.getTileFromCenter(this.x + x, this.y + y).placeElement(this, faction);
    	Map.getTileFromCenter(this.x, this.y).removeElement();
    	this.x += x;
    	this.y += y;
    	Map.getMap();
    	return true;    	
    }

}

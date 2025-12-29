package units;
import main.Game;
import main.ConsoleColors;
import map.Element;
import map.*;
import batiment.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit extends Element implements ConsoleColors{
    // stats of each unit
    public int atk, def, spd, range,  id, faction, basehp, lifetime;
    public boolean aerial;
    public String type = "Unit";
    public static int numOfUnits;
    public HashMap<String, Integer> cost= new HashMap<>();// units can need multiple ressources in order to be deployed
    

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
    	System.out.println(GREEN + "Health: " + RESET + hp);
    	System.out.println(GREEN + "Attack: " + RESET + atk);
    	System.out.println(GREEN + "Defense: " + RESET + def);
    	System.out.println(GREEN + "Speed: " + RESET + spd);
    	System.out.println(GREEN + "Range: " + RESET + range);
    }
    
    public void train( int numOfTrainingGrounds) { // improves stats based on current game level
    	int improveRatio = 10-faction;//makes it so that enemies are a little better than players during current level if they spawn at the same time
    	if(Game.gameLevel <= 10) {
    		improveRatio = Game.gameLevel-faction; 
    	}   	
    	basehp = basehp + 3*improveRatio *( (int) Math.floor(numOfTrainingGrounds * 0.5) );
    	hp = basehp;
    	atk += (int) 2.5*improveRatio *( (int) Math.floor(numOfTrainingGrounds * 0.5) );
    	def += (int) 2.5*improveRatio*( (int) Math.floor(numOfTrainingGrounds * 0.5) );
    }
    
    public void getCost() {
   	 for (String i: cost.keySet()) {
   	        System.out.println(GREEN + i  + " cost: " +RESET+ cost.get(i));
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
        if (cible.isAerial() && !this.isAerial() && this.range == 1) {
        	System.out.println("Could not attack aerial unit!");
            return 0; // Ground units cannot attack aerial units
            //unless they have arrows...
        }
        // no need to check if the target or the attacker is alive because if they are
        // dead they will be deleted from the game

        // i added a random factor to the attacking that makes it so you deal between
        // 80% and 120% of your attack stat
        int damage = (int) (this.atk * (0.8 + Math.random() * 0.4)) - cible.getDef();
        if (damage < 1) {
            damage = 1; // If defense of the target is higher than attack, 1 damage is dealt
        }
        cible.setHp(cible.getHp() - damage);
        if(cible.faction == 1) {
        	Game.updateUnit(cible, Game.playerUnits);
        } else
        	Game.updateUnit(cible, Game.enemyUnits);
        
        return damage;
    }
    
    
    public void checkForUnits(ArrayList<Unit> potentialTargets){//Checks around unit if there are opposing units to attack
    	int leftmost = x - range;
    	int rightmost = x + range;
    	int upmost = y - range;
    	int downmost = y + range;
    	//this is the attack square around the unit. It should attack any enemy who is in that square
    	//if range = 1, it will check in a 3 by 3 square around the unit
    	//if range = 2, it will check in a 5 by 5 square around the unit
    	
    	for (int i = upmost; i<=downmost; i++) {
    		for (int j = leftmost; j<=rightmost; j++) {//Checks around the unit for enemies
    			if (!Map.getTileFromCenter(j, i).isEmpty() && Map.getTileFromCenter(j, i).getElement().elementType == "Unit") {//if tile isn't empty and it contains a unit
    				if(Map.getTileFromCenter(j, i).faction != this.faction) {//If the found unit's faction is different than the attacker
    					potentialTargets.add((Unit)Map.getTileFromCenter(j, i).getElement());// add to potential targets
    				}//we use (Unit) to convert from the superclass Elements to the subclass Unit. It is safe to do so as we made sure the element found was a unit previously
    			}
    		}
    	}
    }

    
    public int Attacking(Batiment cible) {

        int damage = (int) (this.atk);
        cible.hp -= damage;
        Game.updateBuilding(cible, Game.gameBuildings);
    	Map.getMap();
    	if (damage>0) {
			System.out.println(this.icon + " dealt " +RED+ damage + RESET+ " damage to" + cible.type + "!");
			Game.wait(100);
		}
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

    
    public void assignIcon(int faction) {
    	if (faction == Game.PLAYER_FACTION)
            this.icon = GREEN + icon + GREEN_UNDERLINED + String.format("%02d", id)+ RESET;
    	else
            this.icon = RED + icon + RED_UNDERLINED + String.format("%02d", id)+ RESET;
    } //String.format("%02d", id) will print the id with in 2 digits, that way the tile size will be consistent while showcasing up to 100 units at once
    
    
    
}

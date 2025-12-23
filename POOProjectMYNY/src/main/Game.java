package main;
import batiment.*;
import ressources.*;
import map.Map;
import units.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
	static public int gameLevel = 1;
	static public ArrayList<Batiment> gameBuildings = new ArrayList<Batiment>();//current buildings
	static public ArrayList<Unit> playerUnits = new ArrayList<>();//player buildings
	static public ArrayList<Unit> enemyUnits = new ArrayList<>();//enemy buildings

	static public Scanner sc = new Scanner(System.in);
	
	
	static public boolean command(String com) {//true ends turn, false doesn't
		switch(com) {
		case("help"):
			System.out.println("map: Shows the current map.\n stats: Shows stats. unit: generates unit.");
			return false;
		case("soldier"): //just for testing
			System.out.println("X value of unit?");
			int x = sc.nextInt();
			System.out.println("Y value of unit?");
			int y = sc.nextInt();
			return addUnit("Soldat",x,y,1);
		case("map"):
			Map.getMap();
			return false;
		case("build"):
			
		default:
			System.out.println("Unknown command. Perhaps you've typed it in lowercase?");
			return false;
		}
	}
	
	
	static public boolean canAddElem(int x,int y, boolean waterProof) {//checks if its possible to add an element in that position
		if (!Map.inRange(x, y)) {
			System.out.println("You can only place in the currently visible area!"); 
			return false;
			} 
			
		if (!Map.getTileFromCenter(x, y).isEmpty()) {
			System.out.println("Tile is already occupied."); 
			return false;
			} 
		
		if(Map.getTileFromCenter(x, y).getType() == "Water" && !waterProof) {
			System.out.println("You cannot place this on a water terrain!");		
			return false;
			}							
			return true;		
						
				
	}
	
	static public boolean addUnit(String unitToAdd, int x, int y,int faction) {// adds unit at pos (x,y) belonging to faction 1 or -1 (player or computer)
		boolean waterproof;
		if (unitToAdd == "Aigle") {
			waterproof = true;
		} else {
			waterproof = false;
			}			
		
		if (!canAddElem(x,y, waterproof)) {
			return false;}
		
		switch(unitToAdd) {
			case("Soldat"):
				addUnit(new Soldat(), x, y, faction);//function to make code more readable
				break;	
			case("Archer"):
				addUnit(new Archer(), x, y, faction);//function to make code more readable
				break;	
			case("Aigle"):
				addUnit(new Aigle(), x, y, faction);//function to make code more readable
				break;	
			default:
				System.out.println("Not a unit, please try again.");
			}
		
			Map.getMap();
			return true; 	
	}
	
	static void addUnit(Unit u, int x, int y,  int faction) {//adds the unit to both the map and the 
		Map.getTileFromCenter(x, y).placeElement(u, faction);
		if(faction == 1) {
			playerUnits.add(u);
		} else
			enemyUnits.add(u);
	}
		
}

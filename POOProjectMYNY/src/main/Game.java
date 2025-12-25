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
	static public int x,y;
	
	static public boolean command(String com) {//true ends turn, false doesn't
		switch(com) {
		case("help"):
			System.out.println("map: Shows the current map.\n stats: Shows stats. unit: generates unit.");
			return false;
		case("unit"): //just for testing
			System.out.println("X value of unit?");
			x = sc.nextInt();
			System.out.println("Y value of unit?");
			y = sc.nextInt();
			return addUnit("Soldat",x,y,1);
		case("build"):
			System.out.println("X value of building?");
			x = sc.nextInt();
			System.out.println("Y value of building?");
			y = sc.nextInt();
			System.out.println("What to build?");
			String building = sc.next();
			return addBuilding(building, x, y);
		case("map"):
			Map.getMap();
			return false;
		case("stats"):
			ressources.afficher();
			return false;
		case("skip"):
			return true;
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
	
	
	static boolean addBuilding(String build, int x, int y) {
		if (!canAddElem(x,y, false)) {
			return false;}
		
		switch(build) {
		case("quarry"):
		case("q"):
			return addBuilding(new Quarry(), x, y);//function to make code more readable
		case("sawmill"):
		case("s"):
			return addBuilding(new Tree(), x, y);//function to make code more readable
		case("farm"):
		case("f"):
			return addBuilding(new Source(), x, y);//function to make code more readable
		case("training ground"):
		case("training"):
		case("t"):
			return addBuilding(new TrainingGround(), x, y);//function to make code more readable
		default:
			System.out.println("Not a building, please try again.");
			return false;
		}
	}
	
	static boolean addBuilding(Batiment build, int x, int y) {
		if (ressources.getQuantity("Gold") < build.cost) {
			System.out.println("You need " + build.cost + " Gold to build this!");
			return false;
		}
		Map.getTileFromCenter(x, y).placeElement(build, 1);
		build.x = x;
		build.y = y;
		gameBuildings.add(build);
		ressources.consume("Gold", build.cost);
		Map.getMap();
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
				return false;
			}
			
			Map.getMap();
			return true; 	
	}
	
	static void addUnit(Unit u, int x, int y,  int faction) {//adds the unit to both the map and the 
		Map.getTileFromCenter(x, y).placeElement(u, faction);
		u.x = x;
		u.y = y;
		u.faction = faction;
		if(faction == 1) {
			playerUnits.add(u);
		} else
			enemyUnits.add(u);
	}
		
	
	
	static public void updateUnit(Unit unit, ArrayList<Unit> u) {
		for (Unit i: u) {
			if (i.id == unit.id) {
				i.hp = unit.hp;
			}
		}
	}
	static public void updateBuilding(Batiment build, ArrayList<Batiment> u) {
		for (Batiment i: u) {
			if (i.id == build.id) {
				i.hp = build.hp;
			}
		}
	}
}

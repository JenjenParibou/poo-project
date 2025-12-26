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
		switch(com.toLowerCase()) {
		case("h"):
		case("help"):
			System.out.println("LIST OF COMMON COMMAND");
			System.out.println("map: Shows the current map.\nstats: Shows stats.\nunit: generates unit.");
			System.out.println("build: begins building construction.\nmove: move a unit.\nattack:attack with a unit.");
			System.out.println("Refer to game manual for other commands.");
			return false;
		case("u"):
		case("units"):
		case("unit"): //just for testing
			if (!trainingCampExists()) {
				System.out.println("Cannot deploy units without a training camp.");
				return false;
				}
			System.out.println("X value of unit?");
			x = sc.nextInt();
			System.out.println("Y value of unit?");
			y = sc.nextInt();
			System.out.println("What to build?");
			String unit = sc.next();
			return addUnit(unit,x,y,1);
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
		case("move"):
			System.out.println("Please type out the id of the desired unit.");
			int id = sc.nextInt();
			Unit u = findUnitThroughID(id,playerUnits);
			if (u == null) {System.out.println("This id does not exist."); return false;}
			return playerMoveUnit(u);
		default:
			System.out.println("Unknown command.");
			return false;
		}
	}
	
////////////////////// CHECK IF ELEMENT CAN BE ADDED //////////////////////
	
	static public boolean canAddElem(int x,int y, boolean waterProof, int oneIfFriendly) {//checks if its possible to add an element in that position
		if (!Map.inRange(x, y, oneIfFriendly)) {
			if (oneIfFriendly == 1)
				System.out.println("Tile is not currently visible."); 
			return false;
			} 
			
		if (!Map.getTileFromCenter(x, y).isEmpty()) {
			if (oneIfFriendly == 1)
				System.out.println("Tile is already occupied.");
			 
			return false;
			} 
		
		if(oneIfFriendly == 1 && Map.getTileFromCenter(x, y).getType() == "Water" && !waterProof) {
			System.out.println("This cannot go on a water tile.");		
			return false;			
			}	
		
		return true;					
	}
	
////////////////////// BUILDING STUFF //////////////////////
	static boolean addBuilding(String build, int x, int y) {
		if (!canAddElem(x,y, false, 1)) {
			return false;}
		
		switch(build.toLowerCase()) {
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
	
////////////////////// UNIT STUFF //////////////////////
	
	static public boolean addUnit(String unitToAdd, int x, int y,int faction) {// adds unit at pos (x,y) belonging to faction 1 or -1 (player or computer)
				
		switch(unitToAdd.toLowerCase()) {
			case("s"):
			case("soldier"):
			case("soldat"):
				return addUnit(new Soldat(), x, y, faction);//function to make code more readable	
			case("a"):
			case("Archer"):
				return addUnit(new Archer(), x, y, faction);//function to make code more readable
			case("Aigle"):
				return addUnit(new Aigle(), x, y, faction);//function to make code more readable
			default:
				System.out.println("Not a unit, please try again.");
				return false;
			}
	}
	
	static boolean addUnit(Unit u, int x, int y,  int faction) {//adds the unit to both the map and the 
		if (!canAddElem(x,y, u.aerial, faction)) {//checks if element can be added here instead of the previous function because the object already exists, therefor we can use its aerial value
			u = null;//hacky way to delete an object in java, should get eaten by the garbage collector as nothing references it elsewhere
			return false;
			}
		
		for (String i: u.cost.keySet()) {
			if (ressources.currentRessources.get(i) < u.cost.get(i)) {
				System.out.println("You need: " + (u.cost.get(i) -ressources.currentRessources.get(i)) +" of \""+i+"\" in order to deploy this unit.");
				return false;
			}
		}
		for (String i: u.cost.keySet()) {
			if (ressources.currentRessources.get(i) < u.cost.get(i)) {
				ressources.consume(i, u.cost.get(i));
			}
		}	
		Map.getTileFromCenter(x, y).placeElement(u, faction);
		u.x = x;
		u.y = y;
		u.faction = faction;
		if(faction == 1) {
			playerUnits.add(u);
		} else
			enemyUnits.add(u);
		Map.getMap();
		return true; 	
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
	
	static public boolean trainingCampExists() {
		for (Batiment i: gameBuildings) {
			if (i.type == "Training Ground") {
				return true;
			}
		}
		return false;
	}
	
	static Unit findUnitThroughID(int id, ArrayList<Unit> u) {
		for (Unit i: u) {
			if (i.id == id) {
				return i;
			}
		}
		return null;
	}
	
	static boolean playerMoveUnit(Unit u) {
		System.out.println("In what direction? (Up, Down, left, right)");
		String direction = sc.next();
		int rangeChosen = 1;
		if(u.range>1) {
			do {
				System.out.println("By how many squares? (value can go between 1 and " + u.range + ").");
				rangeChosen = sc.nextInt();
			} while (rangeChosen < 1 && rangeChosen > u.range);
		}
		switch(direction.toLowerCase()) {
		case("u"):
		case("up"):
			return u.moveTo(0, -rangeChosen);
		case("d"):
		case("down"):
			return u.moveTo(0, rangeChosen);
		case("l"):
		case("left"):
			return u.moveTo(-rangeChosen, 0);
		case("r"):
		case("right"):
			return u.moveTo(rangeChosen, 0);
		default:
			System.out.println("Invalid direction.");
			return false;
		}
		
	}
	
	
	
	////////////////////// ENEMY STUFF //////////////////////
	
	
	
}

package main;
import batiment.*;
import ressources.*;
import map.Map;
import units.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Game implements ConsoleColors {
	static public int gameLevel = 1, endLevel = 8;
	static public ArrayList<Batiment> gameBuildings = new ArrayList<Batiment>();//current buildings
	static public ArrayList<Unit> playerUnits = new ArrayList<>();//player buildings
	static public ArrayList<Unit> enemyUnits = new ArrayList<>();//enemy buildings
	static public int PLAYER_FACTION = 1;
	static public int ENEMY_FACTION = 0;
	static public int NEUTRAL_FACTION = -1;
	static public Scanner sc = new Scanner(System.in);
	static public int x,y, id;
	static public Unit u; 
	
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
		case("unit"): 
			if (!enemyUnits.isEmpty()) {System.out.println("You cannot deploy units during a fight!"); return false;}
			if (!trainingCampExists()) {
				System.out.println("Cannot deploy units without a working training camp.");
				return false;
				}
			System.out.println("X value of unit?");
			x = scanInt();
			System.out.println("Y value of unit?");
			y = scanInt();
			System.out.println("Which unit to deploy? (Soldier, Archer, Eagle)" );
			String unit = sc.nextLine();
			return addUnit(unit,x,y,PLAYER_FACTION);
		case("b"):
		case("build"):
			if (!enemyUnits.isEmpty()) {System.out.println("You cannot build during a fight!"); return false;}
			System.out.println("X value of building?");
			x = scanInt();
			System.out.println("Y value of building?");
			y = scanInt();
			System.out.println("What to build? (Training Ground, Farm, Sawmill, Quarry)");
			String building = sc.nextLine();
			return addBuilding(building, x, y);
		case("map"):
			Map.getMap();
			return false;
		case("stats"):
		case("stat"):
			ressources.showRessources();
			return false;
		case("sk"):
		case("skip"):
			return true;
		case("m"):
		case("move"):
			System.out.println("Please type out the id of the unit you wish to move.");
			id = scanInt();
			u = findUnitThroughID(id,playerUnits);
			if (u == null) {System.out.println("This id does not exist."); return false;}
			return playerMoveUnit(u);
		case("a"):
		case("atk"):
		case("attack"):
			System.out.println("Please type out the id of your attacking unit.");
			id = scanInt();
			u = findUnitThroughID(id,playerUnits);
			if (u == null) {System.out.println("This id does not exist."); return false;}
			return attackUnits(u);
		case("stats unit"):
		case("sunit"):
		case("su"):
			System.out.println("Please type out the id of your attacking unit.");
			id = scanInt();
			u = findUnitThroughID(id,playerUnits);
			if (u == null)
				u = findUnitThroughID(id,enemyUnits);
			if (u == null) {System.out.println("This id does not exist."); return false;}
			u.showStats();
			return false;
		case("command center"):
		case("command"):
		case("com"):
		case("c"):
			x = Map.getTileFromCenter(0, 0).getElement().hp;
			System.out.println("Your Command Center has " + RED + x + RESET + " HP left.");
			return false;
		default:
			System.out.println("Unknown command.");
			return false;
		}
	}
	
////////////////////// CHECK IF ELEMENT CAN BE ADDED //////////////////////
	
	static public boolean canAddElem(int x,int y, boolean waterProof, int faction, boolean marine) {//checks if its possible to add an element in that position
		if (!Map.inRange(x, y, faction)) {
			if (faction == PLAYER_FACTION)
				System.out.println("Tile is not currently visible."); 
			return false;
			} 
			
		if (!Map.getTileFromCenter(x, y).isEmpty()) {
			if (faction == PLAYER_FACTION)
				System.out.println("Tile is already occupied.");
			 
			return false;
			} 
		if(faction == PLAYER_FACTION && Map.getTileFromCenter(x, y).isAccessible && marine) {
				System.out.println("You cannot place this unit on " + Map.getTileFromCenter(x, y).getType() + " tiles.");		
			return false;			
				}
		
		if(faction == PLAYER_FACTION && !Map.getTileFromCenter(x, y).isAccessible && !waterProof) {
			System.out.println("You cannot place this unit on " + Map.getTileFromCenter(x, y).getType() + " tiles.");		
			return false;			
			}	
		//if unit is a fish, it can only be placed in water
		return true;					
	}
	
////////////////////// BUILDING STUFF //////////////////////
	static boolean addBuilding(String build, int x, int y) {
		if (!canAddElem(x,y, false, PLAYER_FACTION, false)) {
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
		// System.out.println(build.elementType + " " + Map.getTileFromCenter(x, y).getElement().elementType);
		// System.out.println(build.type + " " + Map.getTileFromCenter(x, y).getElement().type);

		gameBuildings.add(build);
		ressources.consume("Gold", build.cost);
		Map.getMap();
		return true;
	}
	
////////////////////// UNIT STUFF //////////////////////
	
	static public boolean addUnit(String unitToAdd, int x, int y,int faction) {// adds unit at pos (x,y) belonging to faction 1 or -1 (player or computer)
	//This is so that the player can add a function just by inputing its name, hence the string			
		switch(unitToAdd.toLowerCase()) {
			case("s"):
			case("soldier"):
			case("soldat"):
				return addUnit(new Soldat(), x, y, faction);//function to make code more readable	
			case("a"):
			case("archer"):
				return addUnit(new Archer(), x, y, faction);//function to make code more readable		
			case("e"):
			case("eagle"):
			case("aigle"):
				return addUnit(new Aigle(), x, y, faction);//function to make code more readable
			case("f"):
			case("fish"):
				return addUnit(new Fish(), x, y, faction);//function to make code more readable	
			case("g"):
			case("giant"):
				return addUnit(new Giant(), x, y, faction);//function to make code more readable
			default:
				System.out.println("Not a unit, please try again.");
				return false;
			}
	}
	
	static boolean addUnit(Unit u, int x, int y,  int faction) {//actually adds the unit to both the map and its respective unit array
		if (!canAddElem(x,y, u.aerial, faction, u.marine)) {//checks if element can be added here instead of the previous function because the object already exists, therefor we can use its aerial value
			u = null;//hacky way to delete an object in java, should get eaten by the garbage collector as nothing references it elsewhere
			Unit.numOfUnits--;
			return false;
			}
		
		if (faction == PLAYER_FACTION) { //only consumes ressources if friendly unit
			for (String i: u.cost.keySet()) {
				if (ressources.currentRessources.get(i) < u.cost.get(i)) {//checks a ressource common to both currentRessources and unit.cost and see if ressources is bigger
					System.out.println("You need: " + (u.cost.get(i) -ressources.currentRessources.get(i)) +" of \""+i+"\" in order to deploy this unit.");
					return false;
				}
			}
			for (String i: u.cost.keySet()) {
				if (ressources.currentRessources.get(i) < u.cost.get(i)) {
					ressources.consume(i, u.cost.get(i));
				}
			}	
		}
		u.assignIcon(faction);//This is mostly so that friendly units can be green and enemy units red
		//must be called before being added to the map or else the icon won't be shown on the map as the old one has already been stored
		u.train(1);//make stats better
		Map.getTileFromCenter(x, y).placeElement(u, faction);
		u.x = x;
		u.y = y;
		u.faction = faction;
		
		if(faction == PLAYER_FACTION) {
			playerUnits.add(u);
			Map.getMap();
		} else
			enemyUnits.add(u);
		return true; 	
	}
		
////////////////////// UPDATING ARRAYS //////////////////////

	
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
	
////////////////////// TRAINING CAMP //////////////////////

	
	static public boolean trainingCampExists() {
		for (Batiment i: gameBuildings) {
			if (i.type == "Training Ground" && i.buildTime <= 0) {
				return true;
			}
		}
		return false;
	}
	
////////////////////// FIND UNIT THROUGH ID IN THE LIST //////////////////////

	
	static Unit findUnitThroughID(int id, ArrayList<Unit> u) {
		for (Unit i: u) {
			if (i.id == id) {
				return i;
			}
		}
		return null;
	}
	
////////////////////// MOVE UNITS //////////////////////

	static boolean playerMoveUnit(Unit u) {
		if(u.spd == 0) {
			System.out.println("This unit cannot move.");
			return false;
		}
		System.out.println("In what direction? (Up, Down, left, right)");
		String direction = sc.nextLine();
		int rangeChosen = 1;
		if(u.spd>1) {
			do {
				System.out.println("By how many squares? (value can go between 1 and " + u.spd + ").");
				rangeChosen = scanInt();
			} while (rangeChosen < 1 || rangeChosen > u.spd);
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
	
	
	static public boolean attackUnits(Unit u) {// if there is a unit to attack, attack it
		ArrayList<Unit> pT = new ArrayList<Unit>();// list that will get filled up will all units around attacking unit
		u.checkForUnits(pT);
		if (pT.size() == 0) {//no unit was found
			if(u.faction == PLAYER_FACTION)
				System.out.println("Couldn't find units to attack.");	
			return false;
			}
		
		Unit u2; //target unit
		
		if (pT.size() == 1) { //check if there's only 1 unit to attack
			u2 = pT.getFirst();
			} else { //more than one
				if(u.faction == PLAYER_FACTION) {//player gets to choose who to attack
					do {
						System.out.println("Type out the id of the enemy unit you wish to attack. (type -1 to cancel the attack, this will not end your turn)");
						id = scanInt();
						sc.nextLine();
						if (id == -1) {
							System.out.println("Attack has been cancelled.");
							return false;
						}		
						u2 = findUnitThroughID(id, pT);
						if (u2 == null)
							System.out.println("No enemies around your unit with such an id.");
					} while(u2 == null);
					
				} else // enemy has to choose who to attack
					u2 = lowestHP(pT);// get unit with lowest hp
			}
		
		
		int damage = u.Attacking(u2);
		if (damage>0) {
			System.out.println(u.icon + " dealt " +RED+ damage + RESET+ " damage to " + u2.icon + "!");
			Game.wait(100);
		}
		return true;
	}
	
	
	static Unit lowestHP(ArrayList<Unit> pT) {//returns unit with the lowest hp in a list
		Unit lowestHP = pT.getFirst();
		for (Unit i: pT) {
			if (lowestHP.hp>i.hp)
				lowestHP = i;
		}
		return lowestHP;	
	}


////////////////////// ENEMY SPAWNING //////////////////////

	static void spawnEnemy() {
		switch(gameLevel) {
		case 1:
			Game.addUnit("Soldier", -2, -Map.visibleGrid, ENEMY_FACTION);
			break;
		case 2:
			Game.addUnit("Soldier", -3, -Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Archer", 3, -Map.visibleGrid, ENEMY_FACTION);
			break;
		case 3:
			Game.addUnit("Soldier", 0, Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Eagle", -2, -Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Soldier", 2, -Map.visibleGrid, ENEMY_FACTION);

			break;
		case 4:
			Game.addUnit("Soldier", -3, -Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Archer", 2, Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Soldier", 2, -Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Soldier", 0, Map.visibleGrid, ENEMY_FACTION);
			break;
		case 5:
			Game.addUnit("Archer",Map.visibleGrid , -4, ENEMY_FACTION);
			Game.addUnit("Soldier", Map.visibleGrid,-2, ENEMY_FACTION);
			Game.addUnit("Soldier",-Map.visibleGrid , 3, ENEMY_FACTION);
			Game.addUnit("Soldier", 1, -Map.visibleGrid, ENEMY_FACTION);
			Game.addUnit("Archer", -1, -Map.visibleGrid, ENEMY_FACTION);
			break;
		case 6:
			Game.addUnit("Archer",4, Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Soldier",Map.visibleGrid-1 , Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Soldier",-Map.visibleGrid+1, Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Eagle",Map.visibleGrid-1 , -Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Soldier",-Map.visibleGrid+1, -Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Archer",-4, -Map.visibleGrid , ENEMY_FACTION);
			break;
		default:;
			Game.addUnit("Soldier",Map.visibleGrid, 0 , ENEMY_FACTION);
			Game.addUnit("Archer",-Map.visibleGrid, 0 , ENEMY_FACTION);
			Game.addUnit("Archer",0, Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Soldier",0, -Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Eagle",-3 , -Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Giant",3 , -Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Eagle",3 , Map.visibleGrid , ENEMY_FACTION);
			Game.addUnit("Soldier",-3 , Map.visibleGrid , ENEMY_FACTION);
			System.out.println(RED + "Final Wave." + RESET);
			break;
		}
		
		Map.getMap();
	}
	
////////////////////// END OF TURN ACTIONS //////////////////////

	
 static public void turnActions(){
	 	boolean playerElementDiedDuringThatTurn = false;//will be set to true if something if a building or player unit has died
	 	playerElementDiedDuringThatTurn = deathBatiment(gameBuildings);//removes any buildings with 0 hp
		 
		 if(!Main.gameOver) {//only executes rest of commands if command center is still alive	
			 if (playerElementDiedDuringThatTurn == false) {//makes sure the boolean if set to false if a building died but not a player
				 playerElementDiedDuringThatTurn = deathUnit(playerUnits);
			 } else
				 deathUnit(playerUnits);
			
			 deathUnit(enemyUnits);
			 
			 HashMap<String, Integer> oldRessources= new HashMap<>();//to check wether resources will change this turn
			 oldRessources.putAll(ressources.currentRessources);
	
			 if(!enemyUnits.isEmpty() && playerElementDiedDuringThatTurn == false)//otherwise enemy would be able to move after killing unit
				 enemyMovement(enemyUnits.getFirst());	
			 
			 if(enemyUnits.isEmpty()) {//only generate ressources if no enemies
				 for(Batiment i: gameBuildings) {	//if building is built and a fight isn't happening, carry its function		 
					 if (i.buildTime>0) {
					 System.out.println(RED + i.type + " is still under construction..."+RESET);
					 i.buildTime--;
					 }else {
						 i.function(10);
						 }
				 }
				 
				 for (String i : ressources.currentRessources.keySet()) {//print resource value if it changed this turn
					 if (ressources.currentRessources.get(i) > oldRessources.get(i)) {
						 	int amount = ressources.currentRessources.get(i) - oldRessources.get(i);
							System.out.println(YELLOW+"Generated " + amount + " " + i + "!"+RESET);
					 }
				 }
			 }	  
		 }
	}
	 
	 
	 
	static boolean deathBatiment(ArrayList<Batiment> b) {
		boolean buildingDied = false;
		for(int i = b.size() - 1; i>= 0; i--) {
			 if(b.get(i).hp <= 0) {
				Map.getTileFromCenter(b.get(i).x, b.get(i).y).removeElement();
				if (b.get(i).type == "Command Center") {//if the destroyed building was the command center, end game
					Main.gameOver = true;
					Map.getMap();
					//System.out.println(RED + "THE COMMAND CENTER HAS BEEN DESTROYED" + RESET);
					System.out.print("\n" + RED);
					for (char c : "THE COMMAND CENTER HAS BEEN DESTROYED".toCharArray()) {
					    System.out.print(c);
					    wait(200);
					}
					
					wait(2000);
					System.out.print("\n" + RESET);
					break;
				}
				System.out.print(b.get(i).icon + " has been destroyed." );
				wait(100);
			 	gameBuildings.remove(i);
				buildingDied = true;
			 }
		 }
		return buildingDied;
	}
	
	
	static boolean deathUnit(ArrayList<Unit> u) {
		boolean unitDied = false;
		for(int i = u.size() - 1; i>= 0; i--) {
			 if(u.get(i).hp <= 0) {	 
				Map.getTileFromCenter(u.get(i).x, u.get(i).y).removeElement();	
			 	Map.getMap();
			 	System.out.print(RESET + "\n");
			 	System.out.print(u.get(i).icon + " has perished." );
			 	wait(100);
			 	u.remove(i);
			 	unitDied = true;
			 }
		 }
		return unitDied;
	}
	
	
	static void enemyMovement(Unit u) {
		if (!attackUnits(u)) {//only move if no opposing units around you
			boolean hasAttacked = false;
			if (u.range != 1) { // if unit has a range above 1 square, try to find command center
				if (Math.abs(u.x) - u.range <= 0 && Math.abs(u.y) - u.range <= 0) {//if centre de commande in range, attack it
					u.Attacking((Batiment)Map.getTileFromCenter(0, 0).getElement());
					hasAttacked = true;// won't move if it attacked command center
				}
			}
			
			if (!hasAttacked) { 
				int movX=0, movY=0;// by how much unit will move in x xor y
				if (u.y<0) {  
					if (u.getType() != "Aigle") {
					movY = 1;// move down if command center below you
					} else { // check if the command center is 1 tile away so it doesnt overshoot
						if (u.y +2 <=0) { 
							movY = 2;
						} else {
							movY = 1;
						}
									
					}}
				else if (u.y>0) {
					if (u.getType() != "Aigle") {
					movY = -1;//move up if command center above you
					} else { // check if the command center is 1 tile away so it doesnt overshoot
						if (u.y -2 >=0) { 
							movY = -2;
						} else {
							movY = -1;
						}
									
					}
					 
					}
				else if (u.x <0) {
					if (u.getType() != "Aigle") {
					movX = 1;// if on the same row as command center, move towards it
					} else { // check if the command center is 1 tile away so it doesnt overshoot
						if (u.x +2 <=0) { 
							movX = 2;
						} else {
							movX = 1;
						}
									
					}
					movX = 1;// if on the same row as command center, move towards it
					}
				else { if (u.getType() != "Aigle") {
					movX = -1;
					} else { // check if the command center is 1 tile away so it doesnt overshoot
						if (u.x -2 >=0) { 
							movX = -2;
						} else {
							movX = -1;
						}
									
					}
					}
	
					if (!Map.getTileFromCenter(u.x + movX, u.y + movY).isEmpty()) {//if the tile we wish to move to isn't empty (contains a building)

							if (Map.getTileFromCenter(u.x+movX, u.y + movY).getElement().elementType == "Building") {
								u.Attacking((Batiment)Map.getTileFromCenter(u.x+movX, u.y + movY).getElement()); // we know it's a building because we used attackUnits earlier so we can use (Batiment)
							} else {
								enemyMovement((Unit)Map.getTileFromCenter(u.x+movX, u.y + movY).getElement());
								}
					} else {// tile is empty
						u.moveTo(movX, movY);	
						}	
				}
			}
		}	
		
	
	
	
	
	
////////////////////// LEVEL INCREASE //////////////////////

	static void levelIncrease() {
		gameLevel++;
		if (gameLevel == endLevel) {
			System.out.println("You've conquered the enemy! Bask in golry as your kingdom remains undefeated for the rest of times!");
			Main.gameOver = true;
		} else {
				
			
			int numOfTrainingGrounds = 0;
			for (Batiment i: gameBuildings) {
				if (i.type == "Training Ground")
					numOfTrainingGrounds++;
			}
			for (Unit i: playerUnits) {
				i.train(numOfTrainingGrounds);	
				i.hp = i.basehp;
				if (numOfTrainingGrounds > 0) {
					System.out.println(i.icon + " has trained! here are their stats: ");
					i.showStats();
					wait(100);
				}
			}
			if (gameLevel <= 12 && (gameLevel+1) % 2 == 0) {
				Map.visibleGrid++;
				System.out.println("Your influence grows, and with it, so does your territory:");
			}
		}
	}
	
	
	
	
	
	public static void wait(int ms) {//function to wait in ms
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}


	public static int scanInt() {//scan ints and makes sure they are actually ints, won't throw errors that way
		
		int y = 0;
		boolean isNum;
		do {
			String x = sc.nextLine();	
			try {
				y = Integer.parseInt(x);
				isNum = true;
			} catch (NumberFormatException e) {
				isNum = false;
			}
		} while (isNum == false);
		
		return y;
	}
}




package main;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import batiment.*;
import map.*; //imports all map related classes, use it for other packages like troops, exp...
import ressources.ressources;
import units.Unit;

public class Main {
	
	public static int turnsBeforeFight = 10; //turns before enemies spawn
	public static boolean gameOver = false;
	
	static public void main(String[] args) {
		titleScreen(); //procedure that contains what happens on the title screen, comment it out for quick start of game
		game();//will start the game
	}
	
	
	

	static public void print(String s) {
		System.out.println(s);
	}
	
	static public void titleScreen() {
		int op;
		Scanner sc = new Scanner(System.in);
		
		logo(); // procedure that draws the game's logo
		print("1. New Game. \n2. Load. (Currently not availible) \n3. Quit.");
		print("Please choose an option by typing a number."); 
		
		do { op = sc.nextInt();sc.nextLine(); // User is prompted to type a number
		//nextLine followed by nextInt to avoid \n and inputmismatch errors
		} while (op < 1 || op > 3); //User may only type a number between 1 and 3
		
		switch (op) {
		case 1:
			game();
			break;
		case 2:
			game();
			break;
		case 3:
			print("See you later!");
			System.exit(0); //ends game.
		}
	}
	
	static public void game() { // aka this is where everything happens
		Scanner sc = new Scanner(System.in);
		
		Map.generateMap();
		print("And thus, your legend begins.");

		ressources.addRessources();
		//Game.addUnit("Soldier", 2, -1, 1);
		//Game.addUnit("Archer", -2, -1, 0);

		Map.getMap();

		
		
		String commande;//the input from the player
		boolean advancer;
		
		
		

		while (!gameOver) {
			System.out.print("\n");
			if(turnsBeforeFight> 1) 
				print(ConsoleColors.RED +turnsBeforeFight+ ConsoleColors.RESET + " turns left before the enemy arrives...");
			else if (turnsBeforeFight== 1) 
				print(ConsoleColors.RED +turnsBeforeFight+ ConsoleColors.RESET + " turn left.");

			print("What will you do?");

			commande = sc.nextLine();//get input from player
			advancer = Game.command(commande);//the game class will output accordingly
			//advancer will be set to true if the player's input is a turn ending action, for example placing a building
			//advancer will be set to false if the player's input isn't a turn ending action, for example viewing the map
			if(advancer) {
				if (turnsBeforeFight > 0)
					turnsBeforeFight--;		
				turnActions();//actions that are taken at the end of a turn, i.e generating gold
				if(gameOver)
					break;
			}

			if (turnsBeforeFight == 0) {
				print(ConsoleColors.RED +"THE ENEMY HAS ARRIVED"+ ConsoleColors.RESET);
				Game.spawnEnemy();
				turnsBeforeFight--;
			}

			if (turnsBeforeFight < 0 && Game.enemyUnits.isEmpty()) {
				System.out.println("The enemy has been vanquished!");
				Game.wait(100);
				Game.levelIncrease();
				turnsBeforeFight = 10;
				Map.getMap();
			}
		}
			
		
		
		
		System.out.println("And so ends your tale.\nGAME OVER");
		System.exit(0); //ends game.
	}
	
	 static public void turnActions(){
		 
		 deathBatiment(Game.gameBuildings);//removes any buildings with 0 hp
		 
		 if(!gameOver) {//only execures rest of commands if command center is still alive	
			 boolean unitDiedDuringThatTurn = false;
			 unitDiedDuringThatTurn = deathUnit(Game.playerUnits);
			 deathUnit(Game.enemyUnits);
			 
			 HashMap<String, Integer> oldRessources= new HashMap<>();//to check wether resources will change this turn
			 oldRessources.putAll(ressources.currentRessources);
	
			 if(!Game.enemyUnits.isEmpty() && unitDiedDuringThatTurn == false)//otherwise enemy would be able to move after killing unit
				 enemyMovement();	
			 
			 if(Game.enemyUnits.isEmpty()) {
				 for(Batiment i: Game.gameBuildings) {	//if building is built and a fight isn't happening, carry its function		 
					 if (i.buildTime>0) {
					 System.out.println(ConsoleColors.RED + i.type + " is still under construction..."+ConsoleColors.RESET);
					 i.buildTime--;
					 }else {i.function(10);}
				 }
				 
				 for (String i : ressources.currentRessources.keySet()) {//print resource value if it changed this turn
					 if (ressources.currentRessources.get(i) > oldRessources.get(i)) {
						 	int amount = ressources.currentRessources.get(i) - oldRessources.get(i);
							System.out.println(ConsoleColors.YELLOW+"Generated " + amount + " " + i + "!"+ConsoleColors.RESET);
					 }
				 }
			 }	  
		 }
	}
	 
	 
	 
	static void deathBatiment(ArrayList<Batiment> b) {
		for(int i = b.size() - 1; i>= 0; i--) {
			 if(b.get(i).hp <= 0) {
				Map.getTileFromCenter(b.get(i).x, b.get(i).y).removeElement();
				if (b.get(i).type == "Command Center") {//if the destroyed building was the command center, end game
					gameOver = true;
					Map.getMap();
					//System.out.println(ConsoleColors.RED + "THE COMMAND CENTER HAS BEEN DESTROYED" + ConsoleColors.RESET);
					System.out.print("\n" + ConsoleColors.RED);
					for (char c : "THE COMMAND CENTER HAS BEEN DESTROYED".toCharArray()) {
					    System.out.print(c);
					    Game.wait(200);
					}
					
					Game.wait(2000);
					System.out.print("\n" + ConsoleColors.RESET);
					break;
				}
				System.out.print(b.get(i).icon + " has been destroyed." );
				Game.wait(100);
			 	Game.gameBuildings.remove(i);
			 }
		 }
	}
	
	
	static boolean deathUnit(ArrayList<Unit> u) {
		boolean unitDied = false;
		for(int i = u.size() - 1; i>= 0; i--) {
			 if(u.get(i).hp <= 0) {	 
				Map.getTileFromCenter(u.get(i).x, u.get(i).y).removeElement();	
			 	Map.getMap();
			 	System.out.print(ConsoleColors.RESET + "\n");
			 	System.out.print(u.get(i).icon + " has perished." );
			 	Game.wait(100);
			 	u.remove(i);
			 	unitDied = true;
			 }
		 }
		return unitDied;
	}
	
	
	static void enemyMovement() {
		Unit u = Game.enemyUnits.getFirst();
		if (!Game.attackUnits(u)) {//only move if no opposing units around you
			if (u.range != 1) { // if unit has a range above 1 square, try to find command center
				if (Math.abs(u.x) - u.range <= 0 && Math.abs(u.y) - u.range <= 0) {//if centre de commande in range, attack it
					u.Attacking((Batiment)Map.getTileFromCenter(0, 0).getElement());
				}
			} else {
			int movX=0, movY=0;// by how much unit will move in x xor y
			if (u.y<0)
				if (Map.getTileFromCenter(u.x, u.y + 1).isEmpty() || (!Map.getTileFromCenter(u.x, u.y + 1).isEmpty() && Map.getTileFromCenter(u.x, u.y + 1).getElement().elementType == "Building"))
					// if tile below you has an enemy unit, don't move there, otherwise movY = 1
					movY = 1;// move down if command center below you
			else if (u.y>0)
				if (Map.getTileFromCenter(u.x, u.y + 1).isEmpty() || (!Map.getTileFromCenter(u.x, u.y + 1).isEmpty() && Map.getTileFromCenter(u.x, u.y + 1).getElement().elementType == "Building"))
					movY = -1;//move up if command center above you
			else if (u.x <0)
				movX = 1;// if on the same row as command center, move towards it
			else
				movX = -1;		

			if (!Map.getTileFromCenter(u.x+movX, u.y + movY).isEmpty()) {//if the tile we wish to move to isn't empty (contains a building)
				u.Attacking((Batiment)Map.getTileFromCenter(u.x+movX, u.y + movY).getElement()); // we know it's a building because we used attackUnits earlier so we can use (Batiment)
			} else {// tile is empty
				u.moveTo(movX, movY);	
			}	
		
		}
		}
		
	}
	
	
	
	
	
	
	
	
	static public void logo() {
		System.out.println(" __  ____     ___   ___     __  _____           _           _     ");
		System.out.println("|  \\/  \\ \\   / / \\ | \\ \\   / / |  __ \\         (_)         | |    "); 
		System.out.println("| \\  / |\\ \\_/ /|  \\| |\\ \\_/ /  | |__) | __ ___  _  ___  ___| |_   ");
		System.out.println("| |\\/| | \\   / | . ` | \\   /   |  ___/ '__/ _ \\| |/ _ \\/ __| __|  ");
		System.out.println("| |  | |_ | |_ | |\\  |_ | |    | |   | | | (_) | |  __/ (__| |_ _ ");
		System.out.println("|_|  |_(_)|_(_)|_| \\_(_)|_|    |_|   |_|  \\___/| |\\___|\\___|\\__(_)");
		System.out.println("                                              _/ |                ");
		System.out.println("                                             |__/                 ");
	}	
	
}

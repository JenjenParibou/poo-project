package main;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import batiment.*;
import map.*; //imports all map related classes, use it for other packages like troops, exp...
import ressources.ressources;
import units.Unit;

public class Main {
	
	static public void main(String[] args) {
		//titleScreen(); //procedure that contains what happens on the title screen, comment it out for quick start of game
		game();//will start the game
	}
	
	
	

	static public void print(String s) {
		System.out.println(s);
	}
	
	static public void titleScreen() {
		int op;
		Scanner sc = new Scanner(System.in);
		
		logo(); // procedure that draws the game's logo (we can change the name later, this is just a placeholder
		print("1. New Game. \n2. Load. \n3. Quit.");
		print("Please choose an option by typing a number."); // Loading does nothing for now, we'll fix it later
		
		do { op = sc.nextInt(); // User is prompted to type a number
		} while (op < 1 || op > 3); //User may only type a number between 1 and 3
		
		switch (op) {
		case 1:
			game();
			break;
		case 2:
			game();//haven't added load function yet
			break;
		case 3:
			print("See you later!");
			break;
		}
	}
	
	static public void game() { // aka this is where everything happens
		Scanner sc = new Scanner(System.in);
		Map.generateMap();
		print("And thus, your legend begins.");
		Map.getMap();

		ressources.addRessources();
		
		
		
		
		String commande;//the input from the player
		boolean advancer;
		boolean gameOver = false;
		
		int turnsBeforeFight = 10; //turns before enemies spawn
		
		while(!gameOver){
			while (!gameOver && turnsBeforeFight > 0) {
				System.out.print("\n");
				if(turnsBeforeFight> 1) 
					print(ConsoleColors.RED +turnsBeforeFight+ ConsoleColors.RESET + " turns left before the enemy arrives...");
					else 
						print(ConsoleColors.RED +turnsBeforeFight+ ConsoleColors.RESET + " turn left.");
				
				print("What will you do?");
				
				commande = sc.next();//get input from player
				advancer = Game.command(commande);//the game class will output accordingly
				//advancer will be set to true if the player's input is a turn ending action, for example placing a building
				//advancer will be set to false if the player's input isn't a turn ending action, for example viewing the map
				if(advancer) {
					turnsBeforeFight--;
					turnActions();
					//actions that are taken at the end of a turn, i.e generating gold
					}
				}
			print(ConsoleColors.RED +"THE ENEMY HAS ARRIVED"+ ConsoleColors.RESET);
		}
	}
	
	 static public void turnActions(){
		 deathBatiment(Game.gameBuildings);//removes any buildings with 0 hp
		 deathUnit(Game.playerUnits);
		 deathUnit(Game.enemyUnits);
		 
		 HashMap<String, Integer> oldRessources= new HashMap<>();//to check whether ressources will change this turn
		 oldRessources.putAll(ressources.currentRessources);

		 for(Batiment i: Game.gameBuildings) {	//if building is built, carry its function		 
			 if (i.buildTime>0) {
			 System.out.println(ConsoleColors.RED + i.type + " is still under construction..."+ConsoleColors.RESET);
			 i.buildTime--;
			 }else {i.function(10); i.function();}
		 }
		 
		 for (String i : ressources.currentRessources.keySet()) {//print resource value if it changed this turn
			 if (ressources.currentRessources.get(i) > oldRessources.get(i)) {
				 	int amount = ressources.currentRessources.get(i) - oldRessources.get(i);
					System.out.println(ConsoleColors.YELLOW+"Generated " + amount + " " + i + "!"+ConsoleColors.RESET);
			 }
		 }
		 
		 
	}
	
	 
	 
	 
	static void deathBatiment(ArrayList<Batiment> b) {
		for(int i = b.size() - 1; i>= 0; i--) {
			 if(b.get(i).hp <= 0) {
				Map.getTileFromCenter(b.get(i).x, b.get(i).y).removeElement();
			 	Game.gameBuildings.remove(i);
			 }
		 }
	}
	
	
	static void deathUnit(ArrayList<Unit> u) {
		for(int i = u.size() - 1; i>= 0; i--) {
			 if(u.get(i).hp <= 0) {
				Map.getTileFromCenter(u.get(i).x, u.get(i).y).removeElement();
			 	u.remove(i);
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

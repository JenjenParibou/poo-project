package main;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import batiment.*;
import map.*; //imports all map related classes, use it for other packages like troops, exp...
import ressources.ressources;
import units.Unit;

public class Main extends Game implements ConsoleColors {
	
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
		print("1. New  \n2. Load. (Currently not availible) \n3. Quit.");
		print("Please choose an option by typing a number."); 
		
		do { op = scanInt(); // User is prompted to type a number
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
			System.exit(0); //ends 
		}
	}
	
	static public void game() { // aka this is where everything happens
		Scanner sc = new Scanner(System.in);
		
		Map.generateMap();
		print("And thus, your legend begins.");

		ressources.addRessources();
		//addUnit("Soldier", 2, -2, ENEMY_FACTION);
		//addUnit("Soldier", 2, 0, ENEMY_FACTION);
		//addUnit("Eagle", 3	, -Map.visibleGrid, ENEMY_FACTION);
		

		Map.getMap();

		
		
		String commande;//the input from the player
		boolean advancer;
		
		
		

		while (!gameOver) {
			System.out.print("\n");
			if(turnsBeforeFight> 1) 
				print(RED +turnsBeforeFight+ RESET + " turns left before the enemy arrives...");
			else if (turnsBeforeFight== 1) 
				print(RED +turnsBeforeFight+ RESET + " turn left.");

			print("What will you do?");

			commande = sc.nextLine();//get input from player
			advancer = command(commande);//the game class will output accordingly
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
				print(RED +"THE ENEMY HAS ARRIVED"+ RESET);
				spawnEnemy();
				turnsBeforeFight--;
			}

			if (turnsBeforeFight < 0 && enemyUnits.isEmpty()) {
				System.out.println("The enemy has been vanquished!");
				wait(100);
				levelIncrease();
				turnsBeforeFight = 10;
				Map.getMap();
			}
		}
			
		
		
		
		System.out.println("And so ends your tale.\nGAME OVER");
		System.exit(0); //ends 
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

package main;
import java.util.Scanner;
import map.*; //imports all map related classes, use it for other packages like troops, exp...

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
		Map.getMap();
		
		String commande;//the input from the player
		boolean advancer;
		
		int turnsBeforeFight = 10; //turns before enemies spawn
		
		while (turnsBeforeFight > 0) {
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
		
	}
	
	 static public void turnActions(){
		//stuff
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

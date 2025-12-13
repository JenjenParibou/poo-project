package main;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		titleScreen(); //procedure that contains what happens on the title screen
		game();
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
			game();
			break;
		case 3:
			print("See you later!");
			break;
		}
	}
	
	static public void game() { // aka this is where everything happens
		
	}
	
}

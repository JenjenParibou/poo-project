package main;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int op;
		
		title();
		print("1. Main menu. \n2. Options. \n3. Quit.");
		print("Please choose an option by typing the number.");
		
		do { op = sc.nextInt();
		} while (op < 1 || op > 3);
		
	}
	
	static public void title() {
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
}

package map;
import java.util.LinkedHashMap;
import main.ConsoleColors;
import java.util.ArrayList;
import java.math.*;
import batiment.*;


public class Terrain { //each cell of the grid is a terrain
	//String[] possibleTypes = {"Grass", "Mountain", "Desert", "Water"};
	
	String type; 
	String icon = " "; //icon of current building or unit
	String color;// green brackets for grass, blue for water..
	int faction = 0; // 0 by default, 1 or -1 depending on who owns it and has a building on it, can't build stuff on it if it's already owned
	int bonus = 1; // mult that increases based on what's happpening?
	ArrayList<Element> currentElement = new ArrayList<Element>();
	//list that holds 1 element: either a building or a unit. can only hold one element (for now)
	
	
	
	public Terrain() {
	}
	
	public void setType(String typeModifier) {
		type = typeModifier;
		//setIcon();
		setColour();
	}
	
	
	public void setColour() { //sets text colour
	switch(type) {
		case("Grass"):
			color = ConsoleColors.GREEN;
			break;
		case("Mountain"):
			color = ConsoleColors.RESET;
		break;
		case("Desert"):
			color = ConsoleColors.YELLOW;
		break;
		case("Water"):
			color = ConsoleColors.CYAN;
		break;
	}
}
	
	
	
//	public void setIcon() {
//		switch(type) {
//			case("Grass"):
//				icon = ".";
//				break;
//			case("Mountain"):
//				icon = "ʌ";
//				break;
//			case("Desert"):
//				icon = ConsoleColors.YELLOW_BOLD + "☼" + ConsoleColors.RESET;
//				break;
//			case("Water"):
//				icon = ConsoleColors.CYAN + "x" + ConsoleColors.RESET;	
//				break;
//		}
//	}
	
	
	
	
	public void setTypeRandom(){
		LinkedHashMap<String, Double> possibleTypes = new LinkedHashMap<>();
		Double proba = Math.random(); // from 0.0 to 1.0
		Double addedTo = 0.0;
		// Weighted probabilities, it's a map containing terrain types with their probabibility of appearing
		possibleTypes.put("Grass", 0.4);
		possibleTypes.put("Mountain", 0.25);
		possibleTypes.put("Desert", 0.25);
		possibleTypes.put("Water", 0.1);
		//All probabilities must add up to 1
//		System.out.println(proba);
		for (String i : possibleTypes.keySet()) {
			
			if (proba <= addedTo + possibleTypes.get(i)) { // 1. proba <= 0.0 + 0.35 // 2. proba <= 0.35 + 0.3 etc.
				setType(i);
				break;
				}	
			 else {
				addedTo += possibleTypes.get(i);
			}
			
		}	
		possibleTypes.clear();
		
	}
	public String getType() { return type;}
	public void buildBuilding(Batiment batiment) {
		if (currentElement.isEmpty()) { // can only have 1 element in list
			currentElement.add(batiment);
			faction = 1;
			//setIcon(); (will uncomment thise once we have building types)
		} else 
			System.out.println("Space is already occupied.");
	}
	
	public void removeElement() {
		if (!currentElement.isEmpty() ) {
			currentElement.remove(0);
			faction = 0;
		}
	}
	
// RANDOM TYPE TEST
//	public static void main(String[] args) {
//		Terrain a = new Terrain();
//		a.setTypeRandom();
//		System.out.println(a.type);
//	}
	
	
}

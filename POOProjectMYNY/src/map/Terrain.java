package map;
import java.util.LinkedHashMap;
import units.*;
import main.ConsoleColors;
import main.Game;

import java.util.ArrayList;
import java.math.*;
import batiment.*;


public class Terrain { //each cell of the grid is a terrain
	//String[] possibleTypes = {"Grass", "Mountain", "Desert", "Water"};
	
	String type; 
	String icon = "   "; //icon of current building or unit
	String color;// green brackets for grass, blue for water..
	public int faction = Game.NEUTRAL_FACTION; // -1 by default, 1 or 0 depending on who owns it and has a building on it, can't build stuff on it if it's already owned
	public double bonus = 1.5; // mult that increases based on what's happpening?
	ArrayList<Element> currentElement = new ArrayList<Element>();
	//list that holds 1 element: either a building or a unit. can only hold one element (for now)
	public boolean isAccessible = true;
	
	
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
		case("Forest"):
			color = ConsoleColors.YELLOW;
		break;
		case("Water"):
			color = ConsoleColors.CYAN;
		break;
	}
}
	
	
	
//	public void setIcon(String faction) {
//		switch(currentElement.getFirst().type) {
//			case("Soldat"):
//				icon = faction + "S" + ConsoleColors.RESET;
//				break;
//			default:
//				icon = " ";
//		}
//	}
	
	
	
	
	public void setTypeRandom(){
		LinkedHashMap<String, Double> possibleTypes = new LinkedHashMap<>();
		Double proba = Math.random(); // from 0.0 to 1.0
		Double addedTo = 0.0;
		// Weighted probabilities, it's a map containing terrain types with their probabibility of appearing
		possibleTypes.put("Grass", 0.4);
		possibleTypes.put("Mountain", 0.25);
		possibleTypes.put("Forest", 0.25);
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
		
		
		switch(type) {//put here all elements you wish to remain inaccessible
		//case("Lava"):
		case("Water"):
			isAccessible = false;
			break;
		}
		
		possibleTypes.clear();
		
	}
	public String getType() { return type;}
	public Element getElement() {return currentElement.getFirst();}

	
	public void placeElement(Element elem, int placerFaction) {
		if (currentElement.isEmpty()) { // can only have 1 element in list
			currentElement.add(elem);

			icon = currentElement.getFirst().icon;
			this.faction = placerFaction;
			
			
		} else 
			System.out.println("Space is already occupied.");
	}
	
	public void removeElement() {
		if (!currentElement.isEmpty()) {
			currentElement.removeFirst();
			icon = "   ";
			faction = Game.NEUTRAL_FACTION;
		}
	}
	
	public boolean isEmpty() {
		return currentElement.isEmpty();
	}
	
// RANDOM TYPE TEST
//	public static void main(String[] args) {
//		Terrain a = new Terrain();
//		a.setTypeRandom();
//		System.out.println(a.type);
//	}
	
	
}

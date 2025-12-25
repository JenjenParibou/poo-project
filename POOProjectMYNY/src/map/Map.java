package map;
import batiment.CommandCenter;
import main.ConsoleColors;
import main.Game;

import java.math.*;




public class Map {
	static final int MAX_CASES = 51; // Must be an odd number
	static final int CENTER = (MAX_CASES - 1) / 2; // or 26 in algorithmic language
	static Terrain[][] Grid = new Terrain[MAX_CASES][MAX_CASES];// array[n*n] of Terrain
	static int visibleGrid = 2; // how many cells are visible around the center
	// will grow every level to signify you taking areas

	
	static public Terrain getTileReal(int xPos, int yPos) { // use this to go through the array
		return(Grid[xPos][yPos]);
	}
	
	// ingame, the origin point of the map will be the center, so (0,0) ingame is equivalent to (25,25) in
	// the array (as in, its center)
	
	static public Terrain getTileFromCenter(int xPos, int yPos) { // so user can type (-1,0) instead of (24, 25)
		return (Grid[yPos + CENTER][xPos + CENTER]);
	}
	
	static public void generateMap() {// randomly generate all tiles
		for (int i = 0; i< MAX_CASES; i++) {
			for (int j = 0; j< MAX_CASES; j++) {
				Grid[i][j] = new Terrain(); //getTileReal didn't work here oddly
				getTileReal(i,j).setTypeRandom();//I love java cause I can just do this
			}
		}
		getTileFromCenter(0,0).setType("Grass");//Forcing type of center to "Grass"
		CommandCenter cc = new CommandCenter();
		getTileFromCenter(0,0).placeElement(cc, 1);
		Game.gameBuildings.add(cc);
	}
	
	static public int westNorthLimit() { // if Center = 25 and visible grid = 3, returns 22 (leftmost / uppermost cell)
		return (CENTER - visibleGrid);
	}
	
	static public int eastSouthLimit() { // if Center = 25 and visible grid = 3, returns 28 (rightmost / downmost cell)
		return (CENTER + visibleGrid);
	}
	
	static public boolean inRange(int x, int y) { // checks if something of an x and y value is inside the visible array
		return ((int)Math.abs(x)<= visibleGrid && (int) Math.abs(y) <= visibleGrid );

	}
	
	static public void getMap() { // print viewable map, will get called each turn
		System.out.print("\n");//can't use println so I do a new line this manually
		for (int i = westNorthLimit() ; i < eastSouthLimit()+1; i++) { // "go from the upmost visible row to the downmost"
			for (int j =  westNorthLimit(); j<eastSouthLimit()+1; j++) {// "go from the leftmost visible row to the rightmost"
				System.out.print(ConsoleColors.colorText("[", getTileReal(i,j).color) + getTileReal(i,j).icon + ConsoleColors.colorText("]", getTileReal(i,j).color));
				// Print( "[ " + Icon of current element + " ]") while also coloring the brackets depending on the type of terrain
			}
			System.out.print("\n"); //new line once we do every column of a row
		}
	}
	
}

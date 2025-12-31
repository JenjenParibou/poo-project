package map;
import batiment.CommandCenter;
import main.ConsoleColors;
import main.Game;
import main.Main;




public class Map implements ConsoleColors {
	static final int MAX_CASES = 21; // Must be an odd number
	static public final int CENTER = (MAX_CASES - 1) / 2; // or 26 in algorithmic language
	static Terrain[][] Grid = new Terrain[MAX_CASES][MAX_CASES];// array[n*n] of Terrain
	public static int visibleGrid = 3; // how many cells are visible around the center
	// will grow every level to signify you taking areas

	

	
	// ingame, the origin point of the map will be the center, so (0,0) ingame is equivalent to (25,25) in
	// the array (as in, its center)
	
	static public Terrain getTileFromCenter(int xPos, int yPos) { // so user can type (-1,0) instead of (24, 25)
		return (Grid[yPos + CENTER][xPos + CENTER]);
	}
	
	static public void generateMap() {// randomly generate all tiles
		for (int i = 0; i< MAX_CASES; i++) {
			for (int j = 0; j< MAX_CASES; j++) {
				Grid[i][j] = new Terrain(); //getTileReal didn't work here oddly
				Grid[i][j].setTypeRandom();
			}
		}
		getTileFromCenter(0,0).setType("Grass");//Forcing type of center to "Grass"
		CommandCenter cc = new CommandCenter();
		getTileFromCenter(0,0).placeElement(cc, 1);
		Game.gameBuildings.add(cc);
	}
	
	/////////////////////////////////
	
	static public int westNorthLimit() { // if Center = 25 and visible grid = 3, returns 22 (leftmost / uppermost visible cell)
		return (CENTER - visibleGrid);
		// this is also how we return fog tiles
	}
	
	static public int eastSouthLimit() { // if Center = 25 and visible grid = 3, returns 28 (rightmost / downmost visible cell)
		return (CENTER + visibleGrid);
		// this is also how we return fog tiles
	}
	
	/////////////////////////////////We can combine west and north / east and south since the visible array is a swear

	
	static public boolean inRange(int x, int y, int oneIfFriendly) { // checks if something of an x and y value is allowed to be on that position
		return ((int)Math.abs(x)<= (visibleGrid- oneIfFriendly) && (int) Math.abs(y) <= (visibleGrid-oneIfFriendly) );
		// OneIfFriendly: set to 1 if element you wish to add is friendly, 0 if it's an enemy
	}
	//Basically, friendly units do NOT have access to the visible map's edges (purple tiles), so their effective action range is the currently visible grid - 1 (as if the we had trimmed the edges of the map for them)
	
	
	static public boolean onFogTile(int x, int y) {//returns true if this position (relative to center) is on the border of the visible map
		return (x == eastSouthLimit() || x == westNorthLimit() || y == eastSouthLimit()|| y == westNorthLimit());
	}
	
	static public void getMap() { // print viewable map, will get called each turn
		System.out.print("\n");//can't use println so I do a new line this manually
		for (int i = westNorthLimit() ; i <= eastSouthLimit(); i++) { // "go from the upmost visible row to the downmost"
			for (int j =  westNorthLimit(); j<=eastSouthLimit(); j++) {// "go from the leftmost visible column to the rightmost"
				// Print( "[ " + Icon of current element + " ]") while also coloring the brackets depending on the type of terrain / if terrain is foggy
				String color = Grid[i][j].color;
				String iconToPrint = Grid[i][j].icon;
				
				if(onFogTile(i,j)) {
					color = PURPLE;
					}
				
				if (Main.gameOver == true) {//makes map look completely purple with red icons if defeated
					if (Map.getTileFromCenter(0, 0).isEmpty()) {
						color = PURPLE;
						iconToPrint = RED + iconToPrint + RESET;
					} else {
						color = GREEN;
						iconToPrint = GREEN + iconToPrint + RESET;
					}
					
				}
					
				// colours edges of border purple (think of it as fog), this is where enemeies will spawn	
				System.out.print(color + "["+ RESET + iconToPrint + color +"]"+ RESET); 
				// will print [ ICON ] for every tile, while coloring brackets according to "color"
			}
			System.out.print("\n"); //new line once we do every column of a row
		}
	}
	
}

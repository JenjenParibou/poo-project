package map;

public class Terrain {
	String type;
	int faction = 0; // 0 by default, 1 or -1 depending on who owns it and has a building on it, can't build stuff on it if it's already owned
	int bonus = 1; // mult that increases based on what's happpening?
	
	public Terrain(String type, int faction, int bonus) {
		this.type = type;
		this.faction = faction;
		this.bonus = bonus;
	}
	
	
	
}

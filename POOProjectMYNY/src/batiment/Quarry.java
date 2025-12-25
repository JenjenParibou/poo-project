package batiment;

public class Quarry extends Generator{
	
	public Quarry() {
		this.type = "Quarry";
		this.cost= 40;
		this.buildTime= 3;
		this.hp= 40;
		this.ressourceType= "Stone";
		this.icon = "S";
		this.preferredTerrain = "Mountain";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}
}

package batiment;

public class Quarry extends Generator{
	
	public Quarry() {
		this.type = "Quarry";
		this.elementType = "Building";
		this.cost= 40;
		this.buildTime= 5;
		this.basehp= 90;
		this.hp= basehp;
		this.ressourceType= "Stone";
		this.icon = "Q";
		this.preferredTerrain = "Mountain";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}
}

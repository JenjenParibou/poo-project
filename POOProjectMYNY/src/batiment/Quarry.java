package batiment;

public class Quarry extends Generator{
	
	public Quarry() {
		this.type = "Quarry";
		this.elementType = "Building";
		this.cost= 40;
		this.buildTime= 3;
		this.basehp= 40;
		this.hp= basehp;
		this.ressourceType= "Stone";
		this.icon = "S";
		this.preferredTerrain = "Mountain";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}
}

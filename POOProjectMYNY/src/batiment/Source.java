package batiment;

public class Source extends Generator{
	
	public Source() {
		this.type = "Farm";
		this.elementType = "Building";
		this.cost= 40;
		this.buildTime= 2;
		this.basehp= 90;
		this.hp= basehp;
		this.ressourceType= "Food";
		this.icon = "F";
		this.preferredTerrain = "Grass";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}
}

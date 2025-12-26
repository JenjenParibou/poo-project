package batiment;

public class Source extends Generator{
	
	public Source() {
		this.type = "Farm";
		this.cost= 40;
		this.buildTime= 2;
		this.hp= 40;
		this.ressourceType= "Food";
		this.icon = "F";
		this.preferredTerrain = "Grass";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}
}

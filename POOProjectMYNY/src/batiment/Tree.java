package batiment;

public class Tree extends Generator{
	
	public Tree() {
		this.type = "Sawmill";
		this.cost= 40;
		this.buildTime= 5;
		this.hp= 40;
		this.ressourceType= "Wood";
		this.icon = "W";
		this.preferredTerrain = "Forest";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}	 

}

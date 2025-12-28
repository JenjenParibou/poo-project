package batiment;

public class Tree extends Generator{
	
	public Tree() {
		this.type = "Sawmill";
		this.elementType = "Building";
		this.cost= 40;
		this.buildTime= 5;
		this.basehp= 90;
		this.hp= basehp;
		this.ressourceType= "Wood";
		this.icon = "W";
		this.preferredTerrain = "Forest";
		this.id = numOfBuildings;
		numOfBuildings++;
		this.icon = " " + icon + " ";
	}	 

}

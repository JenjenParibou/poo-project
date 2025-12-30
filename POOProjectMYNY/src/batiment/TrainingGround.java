package batiment;

public class TrainingGround extends Batiment{
	
	public TrainingGround() {
		this.type = "Training Ground";
		this.elementType = "Building";
		this.cost= 100;
	    this.buildTime= 2;
	    this.basehp = 100;
	    this.hp= basehp;
	    this.icon = "T";
	    this.icon = " " + icon + " ";
		this.id = numOfBuildings;
		numOfBuildings++;
}
}

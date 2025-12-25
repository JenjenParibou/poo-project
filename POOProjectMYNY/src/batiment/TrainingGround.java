package batiment;

public class TrainingGround extends Batiment{
	
	public TrainingGround() {
		this.type = "Training Ground";
		this.cost= 100;
	    this.buildTime= 2;
	    this.hp= 50;
	    this.icon = "T";
		this.id = numOfBuildings;
		numOfBuildings++;
}
}

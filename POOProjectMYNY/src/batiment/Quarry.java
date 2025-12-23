package batiment;

public abstract class Quarry extends Generator{
	
	public Quarry() {
		this.cost= 40;
		this.buildTime= 5;
		this.hp= 40;
		this.ressourceType= "Stone";
	}
}

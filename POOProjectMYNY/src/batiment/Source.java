package batiment;

public abstract class Source extends Generator{
	
	public Source() {
		this.cost= 40;
		this.buildTime= 5;
		this.hp= 40;
		this.ressourceType= "Food";
		this.icon = "F";
	}
}

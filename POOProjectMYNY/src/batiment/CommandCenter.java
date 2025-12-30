package batiment;
import main.ConsoleColors;
import main.Game;
import ressources.ressources;

public class CommandCenter extends Generator {
	
	public CommandCenter() {
		this.icon = "C";
		this.type = "Command Center";
		this.basehp = 250;
		this.hp = basehp;
		this.x = 0;
		this.y = 0;
		this.cost= 999999;
		this.buildTime= 0;
		this.hp= 200;
		this.ressourceType= "Gold";
		this.elementType = "Building";
		this.icon = " " + icon + " ";
		this.id = numOfBuildings;
		numOfBuildings++;
		}	    
	public void function(int r){	
		int numOfFinishedBuildings = 0;
		
		for (Batiment i: Game.gameBuildings) {
			if (i.buildTime <= 0)
				numOfFinishedBuildings++;
			}
		
		ressources.gather( ressourceType, numOfFinishedBuildings * r);
		//System.out.println(ConsoleColors.YELLOW +"Generated " + (int) Game.gameBuildings.size() * r+ " " + ressourceType + "!"+ConsoleColors.RESET );
	}

}

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
		this.hp= 250;
		this.ressourceType= "Gold";
		this.elementType = "Building";
		this.icon = " " + icon + " ";
		}	    
	public void function(int r){		
			ressources.gather( ressourceType, Game.gameBuildings.size() * r);
			//System.out.println(ConsoleColors.YELLOW +"Generated " + (int) Game.gameBuildings.size() * r+ " " + ressourceType + "!"+ConsoleColors.RESET );
	}

}

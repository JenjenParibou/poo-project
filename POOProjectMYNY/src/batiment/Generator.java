package batiment;
import map.Map;
import ressources.ressources;

public abstract class Generator extends Batiment {
	public String ressourceType;
	public String preferredTerrain;
	public double bonus = 1.0;
	
	
	public void function(int r){
		if(Map.getTileFromCenter(x, y).getType() == preferredTerrain)
			this.bonus = Map.getTileFromCenter(x, y).bonus;
			
		int amount = (int) ( bonus * r + (Math.random() * 5));
		ressources.gather( ressourceType, amount);
			//System.out.println(ConsoleColors.YELLOW+"Generated " + amount + " " + ressourceType + "!"+ConsoleColors.RESET);
		} // {System.out.println(ConsoleColors.RED + type + " is still under construction..."+ConsoleColors.RED);}
	
	}


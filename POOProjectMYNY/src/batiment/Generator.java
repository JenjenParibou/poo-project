package batiment;
import ressources.ressources;

public abstract class Generator extends Batiment {
	String ressourceType;
	    
	public void generateRessource(int r){
    ressources.gather(ressourceType, r);
	}


	    

}


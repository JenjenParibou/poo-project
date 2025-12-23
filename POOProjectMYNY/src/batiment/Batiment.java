package batiment;
import map.Element;

public abstract class Batiment extends Element {
	int cost;
	int buildTime;
	
	public abstract void function();
}

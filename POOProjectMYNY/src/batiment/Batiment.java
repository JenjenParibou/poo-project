package batiment;
import map.Element;

public abstract class Batiment implements Element {
	int cost;
	int buildTime;
	
	public abstract void function();
}

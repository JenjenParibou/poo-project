package map;

public abstract class Element {
	public int x, y, hp, id;
	
	public String type;//for building and unit names
	public String elementType; //is either "Building" or "Unit"
	public String icon = " _ ";

}
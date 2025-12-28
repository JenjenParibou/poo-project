package map;

public abstract class Element {
	public int x, y, hp, basehp, id;
	
	public String type;//for building and unit names
	public String elementType; //is either "Building" or "Unit"
	//Added because it would otherwise be really hard to tell whether an element is a unit or a building
	public String icon = " _ ";

}
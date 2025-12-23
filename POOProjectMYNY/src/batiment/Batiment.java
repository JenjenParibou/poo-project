package batiment;
import map.Element;
import java.util.Scanner;

public abstract class Batiment implements Element { //implementing Element interface, will be used later for functions??
	//idk if every building has hp or no, i can just take it out and add it where its needed
	public int cost, buildTime, hp;
	Scanner sc= new Scanner(System.in);
	
	//getters and setters
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(int buildTime) {
		this.buildTime = buildTime;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public abstract void function(); 
	 //ensures that every building has a function, will be instantiating it with every diff building
}

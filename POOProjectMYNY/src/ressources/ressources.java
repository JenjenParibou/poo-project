package ressources;
 
import java.util.HashMap;
import main.ConsoleColors;

public class ressources {


    static public HashMap<String, Integer> currentRessources= new HashMap<>();

    static public void addRessources() { // adds all ressources, put it at the start of main
        currentRessources.put("Wood",40);
        currentRessources.put("Stone",40);
        currentRessources.put("Gold",100);
        currentRessources.put("Food",40);
          }

//     Add a new ressource type
//    public void addResourceType(String type) {
//        currentRessources.putIfAbsent(type, 0);
//    }

    // increase ressource
    static public void gather(String type, int value) {
        if (value <= 0) return;

        currentRessources.put(type, currentRessources.get(type) + value);
    }

    //  remove ressource
    static public boolean consume(String type, int value) {
        if (value <= 0) return false;

        int current = currentRessources.get(type);
        if (current < value) return false;

        currentRessources.put(type, current - value);
        return true;
    }

    static public int getQuantity(String type) {
        return currentRessources.get(type);
    }
// prints out the types and values
    
 static public void showRessources() {
	 for (String i: currentRessources.keySet()) {
	        System.out.println(ConsoleColors.YELLOW + i  + ": " +ConsoleColors.RESET+ currentRessources.get(i));
	    }
 }


//public static void main(String[] args) {
//	
//	ressources R = new ressources();
//	R.afficher();
//	R.gather("Wood",30);
//	R.consume("Stone", 15);
//	R.consume("Food", 300);
//	R.afficher();
//	
//	
//}

}
 
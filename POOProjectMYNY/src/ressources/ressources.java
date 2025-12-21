package ressources;
 
import java.util.HashMap;
import java.util.Map;

public class ressources {

    public Map<String, Integer> resources=new HashMap<>();



    public ressources() {
        resources.put("Wood",150);
        resources.put("Stone",150);
        resources.put("Gold",150);
        resources.put("Food",150);
          }

//    // Add a new ressource type
//    public void addResourceType(String type) {
//        resources.putIfAbsent(type, 0);
//    }

    // increase ressource
    public void gather(String type, int value) {
        if (value <= 0) return;

        resources.put(type, resources.get(type) + value);
    }

    //  remove ressource
    public boolean consume(String type, int value) {
        if (value <= 0) return false;

        int current = resources.get(type);
        if (current < value) return false;

        resources.put(type, current - value);
        return true;
    }

    public int getQuantity(String type) {
        return resources.get(type);
    }
// prints out the types and values
    
 public void afficher() {
	 for (String i: resources.keySet()) {
	        System.out.println(i + ": " + resources.get(i));
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
 
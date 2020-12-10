import java.io.*;
import java.util.*;



public class Location 
{

    private static final String[] ROADHASH = {"Start", "End", "Miles", "Minutes"};

   

    //reads roads.csv and converts data
    public List<HashMap<String, Object>> getRoads(String fileName) throws FileNotFoundException, Exception 
    {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        LinkedList<HashMap<String, Object>> roads = new LinkedList<>();
     
        while(scan.hasNextLine()) {
            HashMap<String, Object> route = new HashMap<>();
            String line = scan.nextLine();
       
            String[] arr = line.split(",");
            for(int i = 0; i < ROADHASH.length; i++) {
                route.put(ROADHASH[i], arr[i]);
            }
    
            roads.add(route);
        }
        return roads;
    }

     //reads attractions.csv and converts it to a HashMap
    public HashMap<String, String> getAttractions(String fileName) throws FileNotFoundException, Exception 
    {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        HashMap<String, String> attractions = new HashMap<>();

        while(scan.hasNextLine()) 
        {
            String line = scan.nextLine();
            String[] arr = line.split(",");
            attractions.put(arr[0], arr[1]);
        }
        return attractions;
    }
}

import java.util.*;


public class RoadTrip 
{

    public static void main(String[] args) throws Exception 
    {
        MapCreator rm = new MapCreator("attractions.csv", "roads.csv");
        LinkedList<String> attractions = new LinkedList<>();
        Scanner s = new Scanner(System.in);

        System.out.println("=======================\nPlan your trip...\n=======================\n");

        System.out.println("Type your starting city (plus state abbreviation, no commas): ");
        String startCity = s.nextLine();

        System.out.println("\nType your ending city (plus state abbreviation, no commas): ");
        String endCity = s.nextLine();

        String lineSeparator = ("------------------------------------------------------------------------------------------\n");
        System.out.println(lineSeparator + "Type any attraction you would like to visit during your trip. When finished, enter 'done'.\n" + lineSeparator);
        
        String attraction = "";
        while(!attraction.equals("done")) 
        {
            System.out.println("Add another Attraction or type 'done': ");
            attraction = s.nextLine().trim();
            if(!attraction.equals("done")) 
            {
                attractions.add(attraction);
                System.out.println("So far, you will visit: " + attractions);
            }
        }

        
        List<String> route = rm.route(startCity, endCity, attractions);
        System.out.println(lineSeparator + "\nThe most efficient route for you planned trip is:\n " + lineSeparator);

        System.out.println(route);
        
    }
}
import java.io.*;
import java.util.*;
import java.lang.*;



public class MapCreator 
{

    private int[][] graph; 
    private HashMap<String, String> attractions; 
    private HashMap<String, Integer> graphLookUpTable; 
    private HashMap<Integer, String> cityLookUpTable;
    private int numCities = 0;

    //constructor
    public MapCreator(String attractionsFile, String roadsFile) throws Exception
    {
        Location lp = new Location();
        List<HashMap<String, Object>> roads = lp.getRoads(roadsFile);
        this.attractions = lp.getAttractions(attractionsFile);
        this.constructLookUpTables(roads);
        this.constructGraph(roads);
    }

    //Uses djikstra's algorithm to find shortest route
    public List<String> route(String startCity, String endCity, List<String> attractions) 
    {
        int size = attractions.size() + 2;
        List<String> visiting = new ArrayList<>(size); //cities to visit
        List<String> visited = new ArrayList<>(size); //cities that have been visited
        visiting.add(startCity);
        if(!startCity.equals(endCity)) 
            visited.add(startCity);

        //add attractions to visiting list
        for (String attraction : attractions) 
        {
            this.graphLookUpTable.get(attraction);
            visiting.add(this.attractions.get(attraction));
        }
        List<String> route = new ArrayList<>(size); //fastest route
        int root = this.graphLookUpTable.get(startCity);

        //uses dijkstra's to find best path from start city to attractions 
        for(int i = 0; i < size - 2; i++) 
        {
            Map<Integer, int[][]> m = this.dijkstra(root, visiting, visited);
            int nextCity = m.entrySet().iterator().next().getKey(); 
            int[][] results = m.get(nextCity); 
            if(nextCity != -1) 
            {
                Stack<String> inRoute = this.citiesInRoute(results, root, nextCity); 
              
                while(!inRoute.empty())
                    route.add(inRoute.pop());
                
                root = nextCity;
                visited.add(this.cityLookUpTable.get(nextCity)); 
            }
        }

        //uses dijkstra's to find best path from ending attraction and city
        visiting.add(endCity); 
        Map<Integer, int[][]> m = this.dijkstra(root, visiting, visited);
        int nextCity = m.entrySet().iterator().next().getKey();
        int[][] results = m.get(nextCity);
        Stack<String> inRoute = this.citiesInRoute(results, root, nextCity);
       
        while(!inRoute.empty())
            route.add(inRoute.pop());
        
        route.add(endCity);
        return route;
    }

    

    //checks efficient route from the algorithm
    private Stack<String> citiesInRoute(int[][] results, int root, int endVertex) 
    {
        Stack<String> routeOrder = new Stack<>();
        if(root == endVertex) 
        {
            return routeOrder;
        }
        int currentVertex = endVertex;
        for(int i = 0; i < this.numCities; i++) 
        {
            currentVertex = results[currentVertex][2]; 
           
            if(currentVertex == root) {
                routeOrder.push(this.cityLookUpTable.get(currentVertex));
                return routeOrder;
            }
            routeOrder.push(this.cityLookUpTable.get(currentVertex));
        }
        return routeOrder;
    }

   //finds closest city and creates table using the algorithm
    private HashMap<Integer, int[][]> dijkstra(int root, List<String> visiting, List<String> visited) 
    {
        //initialize results table
        int[][] results = new int[this.numCities][4];
        for(int i = 0; i < results.length; i++) 
        {
            int[] arr = results[i];
            arr[0] = i; 
            arr[1] = 0; 
            arr[2] = -1; 
            arr[3] = -1;
        }
        HashMap<Integer, int[][]> nextCityAndRoute = new HashMap<>();
        int[] cities = new int[this.numCities]; 
        boolean[] known = new boolean[this.numCities]; 
        for(int i = 0; i < this.numCities; i++) 
        {
            cities[i] = Integer.MAX_VALUE;
            known[i] = false;
        }
        cities[root] = 0; 
        for(int count = 0; count < this.numCities - 1; count++) 
        {
            int chosenVertex = findNextVertex(cities, known); 
           
            if(chosenVertex != Integer.MAX_VALUE && chosenVertex != root && visiting.contains(this.cityLookUpTable.get(chosenVertex)) && !visited.contains(this.cityLookUpTable.get(chosenVertex))) 
            {
                nextCityAndRoute.put(chosenVertex, results);
                return nextCityAndRoute;
            }
            known[chosenVertex] = true; 
            results[chosenVertex][1] = 1;
            for(int v = 0; v < this.numCities; v++) 
            {
                
                if(!known[v] && this.graph[chosenVertex][v] != 0 && cities[chosenVertex] != Integer.MAX_VALUE && cities[chosenVertex] + graph[chosenVertex][v] < cities[v]) {
                    cities[v] = cities[chosenVertex] + this.graph[chosenVertex][v];
                    results[v][2] = chosenVertex;
                    results[v][3] = cities[v];
                }
            }
        }
        return null;         //desired city not found
    }

    //determines what will go next in the algorithm (least cost unknown)
    private int findNextVertex(int[] cities, boolean[] known) 
    {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int v = 0; v < this.numCities; v++) 
        {
           
            if(!known[v] && cities[v] <= min) 
            {
                min = cities[v];
                minIndex = v;
            }
        }
        return minIndex;
    }




    private void constructLookUpTables(List<HashMap<String, Object>> roads) 
    {
        this.graphLookUpTable = new HashMap<>();
        this.cityLookUpTable = new HashMap<>();
        for (HashMap<String, Object> road : roads) 
        {
            String start = (String) road.get("Start"); 
            String end = (String) road.get("End");

             if(this.graphLookUpTable.get(start) == null) 
             {
                this.graphLookUpTable.put(start, this.numCities);
                this.cityLookUpTable.put(this.numCities++, start);

            }
  
            if(this.graphLookUpTable.get(end) == null) 
            {
                this.graphLookUpTable.put(end, this.numCities);
                this.cityLookUpTable.put(this.numCities++, end);
            } 
        }
    }


    //graph is made here
    private void constructGraph(List<HashMap<String, Object>> roads) 
    {
        this.graph = new int[this.numCities][this.numCities];
        for (HashMap<String, Object> route : roads) {
            String startCity = (String) route.get("Start");
            String endCity = (String) route.get("End");
            int start = this.graphLookUpTable.get(startCity); 
            int end = this.graphLookUpTable.get(endCity);
            this.graph[start][end] = Integer.parseInt((String) route.get("Miles"));
            this.graph[end][start] = Integer.parseInt((String) route.get("Miles")); 
        }
    }

  
}
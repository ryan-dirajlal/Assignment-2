
Assignment 2 Design

My assignment will have 3 files:
	1. RoadTrip.java
	2. MapCreator.java
	3. Location.java
In Location, it will have the ability to:
	•Read the given files, attractions.csv and roads.csv
	•Add the data from the files into usable data structures for MapCreator
	•Within Location, there will be the following methods:
		-findAttractions
			This will add the attractions into a hash map.
		-useRoads
			This will add the roads from the given file to a hash map.
In RoadTrip, I will have:
	•The ability for the user to insert starting city, ending city, and any attractions they want to visit
	•Print out the fastest route based on the user’s input

In MapCreator, it will do the following:
	•Use Dijkstra’s algorithm to find the shortest path between cities
		-It will look for the closest city (distance being weight on edge) and then once found, it will set that as the source vertex and restart the algorithm.
	
	•Using method ShortestRoute, it will find the quickest path between the cities and attractions which the user inputted
		-It will start by finding the quickest route from the first city and all attractions
		-Then it will find the quickest route from last attraction to end city

	•After ShortestRoute runs, then Distance method will run. This will:
		-Find the distance traveled from what ShortestRoute returned

	•RoutedCities will use a stack to determine the best route of cities that dijkstra's algorithm visited
		-Similar to how we read the graph during class lecture, this will backtrack through the table to read the graph

	•HashMap includes dijkstra's algorithm that stops when the nearest city has been visited
		-It will return a hashmap
		-It will use what we learned in class to go through the algorithm
 
	•The NextVertex method will be implemented to help with HashMap
		-It will determine the least cost unknown vertex


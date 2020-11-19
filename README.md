# OOP - ex1
### Ex1 contains the following classes:
- WGraph_DS- implementing weighted_graph and node_info interfaces.
- WGraph_Algo- implementing weighted_graph_algorithms interface.

WGraph_DS class represents an undirected weighted graph, represented by a HashMap.
The reason I chose to represent the graph in a HashMap is because it allows easy access to each node in the graph, therefor questions like whether two nodes are connected 
and how many edges are in the graph can be answered in O(1). Adding a new node to the graph, or connecting two nodes in the graph with an edge, can also be done easily in O(1). 
Connecting simply requires adding each of the nodes to the other node's neighbors list with the wanted weight, and removing an edge requires removing each of the nodes from 
the other node's neighbors list. 

WGraph_Algo class represents the regular Graph Theory algorithms including:
- clone(); (copy)
- init(graph);
- isConnected(); - using the BFS algorithm.
- double shortestPathDist(int src, int dest); - using the Dijkstra algorithm.
- List<node_info> shortestPath(int src, int dest); - using the Dijkstra algorithm.
- Save(file); - using the java.io.Serializable interface.
- Load(file); - using the java.io.Serializable interface.

### The Dijkstra algorithm
In order to find the distance of the shortest path in the graph I used the Dijkstra algorithm, which allows to find the shortest path in an udirected weighted graph in 
a fast and efficient way.          
For more information on the algorithm: https://brilliant.org/wiki/dijkstras-short-path-finder/

![Weighted Graph](https://ds055uzetaobb.cloudfront.net/brioche/uploads/ydOEDFABWr-graph1.png?width=2400)

package ex1.src;
import java.io.*;
import java.util.*;
/**
 * This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_info> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 */
public class WGraph_Algo implements weighted_graph_algorithms{

    private weighted_graph w_graph_ds;


    public WGraph_Algo(){
        w_graph_ds= new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.w_graph_ds= g;
    }

    @Override
    public weighted_graph getGraph() {
        return w_graph_ds;
    }

    /**
     * Computes a deep copy of this graph, using a copy constructor in the WGraph_DS class.
     * @return
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS ans= new WGraph_DS((WGraph_DS) w_graph_ds);
        return ans;
    }

    /**
     * Returns true iff the graph is connected.
     * This function uses the BFS algorithm, which sets the tag of each node in the graph
     * as an int, representing the number of nodes between the node and the node that the BFS function
     * is using. This function is activating the BFS algorithm by using the first node in the graph,
     * but the function can work using any other node in the graph as well.
     * After each node in the graph has a tag, the function is checking whether there's a node
     * in the graph with the tag -1 , which means there isn't a valid path to this node from any other
     * node - because the graph is not connected.
     * @return true iff the graph is connected.
     */
    @Override
    public boolean isConnected() {
        if(w_graph_ds.getV().size() <= 1) return true;
        if(w_graph_ds.edgeSize() < w_graph_ds.nodeSize()-1) return false;

        Iterator<node_info> it = w_graph_ds.getV().iterator();
        node_info temp = it.next();
        bfs(temp);

        for(node_info n: w_graph_ds.getV()){
            if(n.getTag() == -1) return false;
        }
        return true;
    }

    /**
     * Returns the length of the shortest path between two nodes- source and destination.
     * This function is using the Dijkstra algorithm. The Dijkstra function is getting the source node and
     * the destination node and is setting the tag of every node in the path between them as the distance
     * from the source node. Until the algorithm finds all the ways to get to the destination node
     * and sets the tag of destination node with the distance of the shortest path.
     * Then this function returns the tag of the destination node.
     * If there isn't a valid path between the two nodes the function returns -1.
     * @param src - start node.
     * @param dest - end node.
     * @return the distance between the two nodes.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        if (w_graph_ds.getV().contains(w_graph_ds.getNode(src)) && w_graph_ds.getV().contains(w_graph_ds.getNode(dest))) {
            node_info start = w_graph_ds.getNode(src);
            node_info end = w_graph_ds.getNode(dest);
            return DijkstraAlgo(start, end);
        }
        return -1;
    }

    /**
     * Returns the shortest path between src to dest as an ordered List of nodes.
     * This function is using the Dijkstra algorithm to set the tag of the destination node (and all
     * the other nodes in the path) as the shortest distance from the source node. When a node
     * gets the right tag the function is setting the info of the node as the key of the previous
     * node in the path. Then with the getPath function the list is created by adding the destination
     * node and then adding the node with the key from the destination node's info, this process
     * continues until the function gets to the source node.
     * In case there isn't a valid path between the two nodes the function returns null.
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_info> of the shortest path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(src == dest) {
            LinkedList<node_info> ans= new LinkedList<>();
            ans.add(w_graph_ds.getNode(src));
            return ans;
        }
        if(shortestPathDist(src, dest) == -1) return null;

        return getPath(w_graph_ds.getNode(src), w_graph_ds.getNode(dest));
    }

    /**
     * Saves this weighted (undirected) graph to the given file name.
     * @param file - the file name.
     * @return true - iff the file was successfully saved.
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream FOut = new FileOutputStream(file);
            ObjectOutputStream Out = new ObjectOutputStream(FOut);
            Out.writeObject(this.w_graph_ds);
            Out.close();
            FOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method loads a graph to this graph algorithm.
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream FIn = new FileInputStream(file);
            ObjectInputStream OIn = new ObjectInputStream(FIn);

            w_graph_ds = (weighted_graph) OIn.readObject();
            FIn.close();
            OIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private double DijkstraAlgo(node_info start, node_info end){
        PriorityQueue<node_info> q= new PriorityQueue<>(Comparator.comparingDouble(node_info::getTag));

        for (node_info node : w_graph_ds.getV()) {
            node.setInfo("null");
            node.setTag(Double.MAX_VALUE);
        }
        start.setTag(0);
        q.add(start);
        while (!q.isEmpty()) {

            node_info curr= q.remove();
            if (!curr.getInfo().contains("visited")) {
                curr.setInfo(curr.getInfo() + " visited");
                if (curr.getKey() == end.getKey()) {
                    return curr.getTag();
                }

                for (node_info neighbor : w_graph_ds.getV(curr.getKey())) {
                    if (!neighbor.getInfo().contains("visited")) {
                        double distance = curr.getTag() + w_graph_ds.getEdge(curr.getKey(), neighbor.getKey());
                        if (distance < neighbor.getTag()) {
                            neighbor.setTag(distance);
                            neighbor.setInfo(curr.getKey() + "");
                            q.add(neighbor);
                        }
                    }
                }
            }
        }

        return -1;
    }


    private List<node_info> getPath(node_info start, node_info end){
        LinkedList<node_info> ans= new LinkedList<>();
        if(end.getTag() == Double.MAX_VALUE) return null;

        if(start == end){
            ans.add(start);
            return ans;
        }

        node_info temp= end;
        String info="";
        while (temp != start){
            ans.addFirst(temp);
            info= temp.getInfo();
            String[] getKey = info.split(" ");
            int key= Integer.parseInt(getKey[0]);
            temp= w_graph_ds.getNode(key);
        }
        ans.addFirst(start);

        return ans;
    }


    private void bfs(node_info start) {
        Queue<node_info> q1 = new LinkedList<>();

        for (node_info node : w_graph_ds.getV()) {
            node.setInfo("white");
            node.setTag(-1);
        }
        start.setInfo("gray");
        start.setTag(0);
        q1.add(start);

        while (!q1.isEmpty()) {
            node_info u = q1.remove();
            if (w_graph_ds.getV(u.getKey()) != null && u != null) {
                for (node_info neighbor : w_graph_ds.getV(u.getKey())) {
                    if (neighbor.getInfo() == "white") {
                        neighbor.setInfo("gray");
                        neighbor.setTag(u.getTag() + 1);
                        q1.add(neighbor);
                    }
                }
            }
            u.setInfo("black");
        }
    }







}

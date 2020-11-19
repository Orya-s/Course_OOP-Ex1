package ex1.src;



import java.util.*;

/**
 * This class represents an undirected weighted graph, represented by a HashMap.
 */
public class WGraph_DS implements weighted_graph, java.io.Serializable {

    private HashMap<Integer, node_info> graph;
    private int edges, mc;


    public WGraph_DS(){
        graph= new HashMap<>();
        this.edges= 0;
        this.mc= 0;
    }

    /**
     * A copy constructor.
     * This function creates a new HashMap and new nodes with the original keys
     * from graph g. After creating all the nodes the function is connecting all the edges
     * that exist in graph g with the same weight.
     * The function also copies the number of edges and the number of MC.
     * @param g
     */
    public WGraph_DS(WGraph_DS g){
        this.graph= new HashMap<>();
        for(node_info n: g.getV()) {   //Building new nodes for the new graph, keeping the keys
            node temp= new node(n.getKey());
            temp.setTag(n.getTag());
            temp.setInfo(n.getInfo());
            this.graph.put(temp.getKey(), temp);
        }

        for(node_info n: g.getV()) {   //Setting the list of neighbors for each node
            for(node_info neighbor: g.getV()) {
               if(g.hasEdge(n.getKey(), neighbor.getKey())) {
                   double w= g.getEdge(n.getKey(), neighbor.getKey());
                   connect(n.getKey(), neighbor.getKey(), w);
               }
            }
        }

        this.mc= g.mc;
        this.edges= g.edges;
    }

    /**
     * This function allows easy access to the nodes in the graph.
     * @param key - the node_id
     * @return node_info(key).
     */
    @Override
    public node_info getNode(int key) {
        if(!graph.containsKey(key)) return null;
        return graph.get(key);
    }

    /**
     * This function returns true when two nodes have an edge between them.
     * @param node1
     * @param node2
     * @return True if connected, False if not connected.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1==node2) return false;
        if(!graph.containsKey(node1) || !graph.containsKey(node2)) {
            return false;       // Returns false if one of the node's keys is not in the graph.
        }

        return ((node)(getNode(node1))).hasNi(node2) && ((node)(getNode(node2))).hasNi(node1);
    }

    /**
     * Returns the weight of the edge (node1, node1). In case there is no such edge
     * should return -1.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(!hasEdge(node1, node2)) return -1;

        node nodeA= (node)getNode(node1);
        return nodeA.getEdgeWeight(node2);
    }

    /**
     * This function adds a new node to the graph. If the node is already in the graph
     * the MC number stays the same.
     * @param key
     */
    @Override
    public void addNode(int key) {
        node newNode = new node(key);

        int node_size = nodeSize();
        graph.put(key, newNode);
        if (node_size != nodeSize()){  //if the nodeSize is the same- the node is already int the graph-
            mc++;                      // so mc shouldn't change
        }
    }

    /**
     * Connecting two nodes in the graph. If one of the nodes is not in the graph the function does nothing.
     * If there's already an edge the weight is updated. If the weight is the same the function does nothing.
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(graph.containsKey(node1) && graph.containsKey(node2) && node1!=node2){
            if(!hasEdge(node1, node2)){    //if there's an edge already- the number of edges doesn't change
                edges++; mc++;
            }
            else {
                if(getEdge(node1, node2) != w){   //if the edge's weight is the same- the number of mc doesn't change
                    mc++;
                }
            }
            node nodeA= (node)getNode(node1);
            node nodeB= (node)getNode(node2);
            nodeA.addNi(node2, w);
            nodeB.addNi(node1, w);
        }
    }

    /**
     * This function returns a collection representing all the nodes in the graph.
     * @return Collection<node_info>.
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values();
    }

    /**
     * This function returns a collection representing all the nodes connected to node_id.
     * @param node_id
     * @return Collection<node_info>.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        LinkedList<node_info> neighbors= new LinkedList<>();
        if(!graph.containsKey(node_id)) {
            return neighbors;
        }
        node nodeA= (node)getNode(node_id);
        HashMap Ni= nodeA.getNi();
        Iterator it = Ni.keySet().iterator();
        while(it.hasNext()) {
            Object temp = it.next();
            neighbors.add(graph.get(temp));
        }
        return neighbors;
    }

    /**
     * This function is deleting the node (with the given ID) from the graph,
     * and removing all edges which start or end at this node.
     * @param key
     * @return the deleted node.
     */
    @Override
    public node_info removeNode(int key) {
        if (!graph.containsKey(key)) return null;
        node_info ans = getNode(key);

        Iterator<node_info> it = getV(key).iterator();
        while(it.hasNext()) {
            node_info temp = it.next();
            removeEdge(temp.getKey(), key);
            it = getV(key).iterator();
        }

        graph.remove(key, getNode(key));
        mc++;
        return ans;
    }

    /**
     * Removing an edge between two nodes in the graph.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)) {
            node nodeA= (node)getNode(node1);
            node nodeB= (node)getNode(node2);
            nodeA.removeNode(node2);
            nodeB.removeNode(node1);
            edges--; mc++;
        }
    }

    /**
     * @return the number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return edges;
    }

    @Override
    public int getMC() {
        return mc;
    }

    public String toString() {
        return "Graph- " + graph;
    }

    /**
     * @param o
     * @return true if Object o equals to this WGraph_DS.
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        WGraph_DS g = (WGraph_DS)o;
        return graph.equals(g.graph) && edges == g.edges;
    }


    /**
     * This class represents the set of operations applicable on a
     * node in an undirected - weighted graph.
     */
    private class node implements node_info, java.io.Serializable {

        private int key;
        private String info= "";
        private double tag;
        private HashMap<Integer, Double> Ni;


        /**
         * Setting a unique key for each node,
         * and creating a map to hold the list of neighbors of each node.
         * Every entry in the map contains the neighbor's unique key and the weight of the
         * edge between this node and the neighbor.
         */
        public node(int key){
            this.key= key;
            Ni= new HashMap<>();
        }

        /**
         * Return true iff this<==>key are adjacent - has an edge between them.
         * @param key
         * @return
         */
        public boolean hasNi(int key) {
            return Ni.containsKey(key);
        }

        /**
         * @param key
         * @return the weight of the edge between this node and the neighbor.
         */
        public double getEdgeWeight(int key){
            return Ni.get(key);
        }

        /**
         * This function adds node_info(key) to the list of this node's neighbors, with the specific weight.
         * @param key
         * @param edgeWeight
         */
        public void addNi(int key, double edgeWeight){
            Ni.put(key, edgeWeight);
        }

        public HashMap getNi(){
            return Ni;
        }

        /**
         * This function removes node_info(NiKey) from the list of this node's neighbors.
         * @param NiKey
         */
        public void removeNode(int NiKey) {
            Ni.remove(NiKey);
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info= s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag= t;
        }

        public String toString(){
            return  "Node- Key:" + key ;
        }

        /**
         * @param o
         * @return true if Object o equals to this node.
         */
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;

            node curr = (node)o;
            return key == curr.key && tag == curr.tag && info.equals(curr.info) && Ni.equals(curr.Ni);
        }
    }
}
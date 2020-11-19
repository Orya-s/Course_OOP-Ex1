package ex1.tests;
import ex1.src.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

class WGraph_AlgoTest {


    @Test
    void init() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,0,1.0);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);
        g.connect(0,3,1.0);

        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);

        assertEquals(4, ga.getGraph().nodeSize());
        assertEquals(3, ga.getGraph().edgeSize());
        assertTrue(ga.getGraph().hasEdge(0, 1));
        assertFalse(ga.getGraph().hasEdge(0, 0));
        assertFalse(ga.getGraph().hasEdge(1, 2));
    }

    @Test
    void getGraph() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        assertEquals(g, ga.getGraph());

        g.removeNode(0);
        assertEquals(g, ga.getGraph());
    }

    @Test
    void copy() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        weighted_graph g2= ga.copy();

        g.addNode(4);
        g2.removeNode(0);

        assertNotEquals(g.nodeSize(),g2.nodeSize());
        assertNotEquals(g.edgeSize(),g2.edgeSize());
        assertNotEquals(g,g2);
    }

    @Test
    void isConnected() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        assertTrue(ga.isConnected());

        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        assertFalse(ga.isConnected());

        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);
        assertTrue(ga.isConnected());

        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.connect(4,5,1.0);
        g.connect(5,6,1.0);
        assertFalse(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.connect(0,1,0.5);
        g.connect(2,4,3);
        g.connect(4,5,10);
        g.connect(5,3,2);
        g.connect(3,6,4);
        g.connect(2,6,1);
        g.connect(5,7,1);

        double dist= ga.shortestPathDist(2,5);
        assertEquals(7, dist);
        dist= ga.shortestPathDist(0,0);
        assertEquals(0.0, dist);
        dist= ga.shortestPathDist(6,7);
        assertEquals(7, dist);
        dist= ga.shortestPathDist(0,2);
        assertEquals(-1.0, dist);
    }

    @Test
    void shortestPath() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga= new WGraph_Algo();
        ga.init(g);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.connect(0,1,0.5);
        g.connect(2,4,3);
        g.connect(4,5,10);
        g.connect(5,3,2);
        g.connect(3,6,4);
        g.connect(2,6,1);
        g.connect(5,7,1);

        List<node_info> checkList= new LinkedList<>();

        List<node_info> path= ga.shortestPath(0,0);
        checkList.add(g.getNode(0));
        assertEquals(checkList, path);

        path= ga.shortestPath(2,5);
        checkList= new LinkedList<>();
        checkList.add(g.getNode(2));
        checkList.add(g.getNode(6));
        checkList.add(g.getNode(3));
        checkList.add(g.getNode(5));
        assertEquals(checkList, path);

        path= ga.shortestPath(0,1);
        checkList= new LinkedList<>();
        checkList.add(g.getNode(0));
        checkList.add(g.getNode(1));
        assertEquals(checkList, path);

        path= ga.shortestPath(0,2);
        assertNull(path);
    }

    @Test
    void save() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        String str = "g.obj";
        ga.save(str);

        weighted_graph g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);

        ga.load(str);
        assertEquals(ga.getGraph(),g1);
    }

    @Test
    void load() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,0.5);

        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        String str = "g.obj";
        ga.save(str);

        weighted_graph g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0,1,0.5);
        assertEquals(ga.getGraph(),g1);
        ga.load(str);
        assertEquals(g, ga.getGraph());
        g.removeNode(0);
        assertNotEquals(g, ga.getGraph());
    }


}
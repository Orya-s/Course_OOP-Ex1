package ex1.tests;
import ex1.src.*;


import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void getNode() {
        weighted_graph g = new WGraph_DS();
        node_info n= g.getNode(1);
        assertNull(n);

        g.addNode(0);
        node_info node= g.getNode(0);
        assertEquals(0,node.getKey());
    }

    @Test
    void hasEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0,1,1.0);
        boolean a= g.hasEdge(0,1);
        boolean b= g.hasEdge(1,0);
        assertTrue(a);
        assertTrue(b);

        boolean c= g.hasEdge(1,1);
        assertFalse(c);

        boolean d= g.hasEdge(0,2);
        boolean e= g.hasEdge(2,3);
        assertFalse(d);
        assertFalse(e);

        g.addNode(2);
        g.addNode(3);
        boolean f= g.hasEdge(2,3);
        assertFalse(f);
    }

    @Test
    void getEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0,1,0.5);
        assertEquals(0.5, g.getEdge(0,1));
        assertEquals(0.5, g.getEdge(1,0));
        assertEquals(-1, g.getEdge(0,0));
        assertEquals(-1, g.getEdge(1,2));
    }

    @Test
    void addNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        node_info n= g.getNode(1);
        assertEquals(1, n.getKey());
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,0,1.0);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        assertEquals(-1,g.getEdge(0,0));
        assertTrue(g.hasEdge(0, 1));

        g.removeEdge(0,1);
        assertFalse(g.hasEdge(0, 1));
        assertFalse(g.hasEdge(2, 1));

        double w = g.getEdge(2,0);
        assertEquals(1,w);

        g.connect(0,2,2);
        w = g.getEdge(2,0);
        assertEquals(2,w);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(3);
        g.addNode(3);

        Collection<node_info> ans= new LinkedList<>();
        ans.add(g.getNode(0));
        ans.add(g.getNode(1));
        ans.add(g.getNode(2));
        ans.add(g.getNode(3));

        assertEquals(ans.size(), g.getV().size());

        ans.remove(g.getNode(0));
        g.removeNode(0);

        assertEquals(ans.size(), g.getV().size());
    }

    @Test
    void testGetV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,0,1.0);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);
        g.connect(0,3,1.0);

        g.connect(0,4,1.0);

        Collection<node_info> ans= new LinkedList<>();
        ans.add(g.getNode(1));
        ans.add(g.getNode(2));
        ans.add(g.getNode(3));

        assertEquals(ans.size(), g.getV(0).size());

        ans.remove(g.getNode(3));
        g.removeEdge(0,1);

        assertEquals(ans.size(), g.getV(0).size());
    }

    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        g.removeNode(4);
        assertEquals(4,g.nodeSize());

        g.removeNode(3);
        g.removeNode(3);
        assertEquals(3,g.nodeSize());

        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));

        assertEquals(0,g.edgeSize());
        assertEquals(2,g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);

        g.removeEdge(4,5);
        g.removeEdge(0,3);
        g.removeEdge(0,3);
        assertFalse(g.hasEdge(0,3));
        assertFalse(g.hasEdge(3,0));
        assertEquals(2, g.edgeSize());
        assertEquals(-1, g.getEdge(0,3));
    }

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(2);

        g.removeNode(3);
        g.removeNode(2);
        g.removeNode(0);
        g.removeNode(0);
        assertEquals(1,g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,0,1.0);
        g.connect(0,1,1.0);
        g.connect(0,2,1.0);
        g.connect(0,3,1.0);
        g.connect(0,3,1.0);

        g.connect(0,4,1.0);
        assertEquals(3, g.edgeSize());

        g.connect(0,1,2.0);
        assertEquals(3, g.edgeSize());
    }

    @Test
    void getMC() {
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

        assertEquals(7,g.getMC());

        g.connect(0,3,1.0);
        assertEquals(7,g.getMC());

        g.connect(0,2,2);
        assertEquals(8,g.getMC());

        g.removeEdge(0,0);
        g.removeEdge(0,1);
        g.removeEdge(0,1);
        assertEquals(9,g.getMC());

        g.removeNode(3);
        assertEquals(11,g.getMC());
    }



}
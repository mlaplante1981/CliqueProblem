package unit;

import static org.junit.Assert.*;
import laplante.michael.clique.Clique;
import laplante.michael.clique.Node;
import laplante.michael.clique.NodeList;
import laplante.michael.clique.Graph;

import org.junit.Test;


public class TestGraphFunctions {

	@Test
	public void testNodeNames(){
		Graph nm = new Graph();
		for (int i = 1; i<=26; i++){
			nm.newNode();
		}
		Node n1 = nm.newNode();
		assertTrue(n1.getName().equalsIgnoreCase("aa"));
		Node n2 = nm.newNode();
		assertTrue(n2.getName().equalsIgnoreCase("ab"));
		Node n3 = nm.newNode();
		assertTrue(n3.getName().equalsIgnoreCase("ac"));
		nm.initiateNeighborIntroductions();
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test
	public void newNodeWithName(){
		Graph nm = new Graph();
		
		Node n1 = nm.newNode("N1");
		Node n2 = nm.newNode("N2");
		Node n3 = nm.newNode("N3");
		Node n4 = nm.newNode("N4");
		Node n5 = nm.newNode("N5");
		
		nm.connectNodes(n1,n2,n3,n4,n5);
		
		nm.initiateNeighborIntroductions();
		
		assertTrue(n1.hasNeighbor(n2));
		assertTrue(n1.hasNeighbor(n3));
		assertTrue(n1.hasNeighbor(n4));
		assertTrue(n1.hasNeighbor(n5));
		
		assertTrue(n1.hasNeighbors(n2,n3,n4,n5));
		assertTrue(n2.hasNeighbors(n1,n3,n4,n5));
		assertTrue(n3.hasNeighbors(n2,n1,n4,n5));
		assertTrue(n4.hasNeighbors(n2,n3,n1,n5));
		assertTrue(n5.hasNeighbors(n2,n3,n4,n1));
		
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test
	public void grid3x3(){
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		Node f = nm.newNode();
		Node g = nm.newNode();
		Node h = nm.newNode();
		Node i = nm.newNode();
		Node j = nm.newNode();
		Node k = nm.newNode();
		Node l = nm.newNode();
		
		nm.connectNodes(a, b);
		nm.connectNodes(b, c);
		nm.connectNodes(c, f);
		nm.connectNodes(f, i);
		nm.connectNodes(i, h);
		nm.connectNodes(h, g);
		nm.connectNodes(g, d);
		nm.connectNodes(d, a);
		
		nm.connectNodes(e, a);
		nm.connectNodes(e, b);
		nm.connectNodes(e, c);
		nm.connectNodes(e, d);
		nm.connectNodes(e, f);
		nm.connectNodes(e, g);
		nm.connectNodes(e, h);
		nm.connectNodes(e, i);
		
		nm.initiateNeighborIntroductions();
		
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test
	public void one3clique(){
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		
		nm.connectNodes(a, b);
		nm.connectNodes(b, c);
		nm.connectNodes(c, a);
		
		nm.initiateNeighborIntroductions();
		
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test
	public void bunchO3cliques(){
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		Node f = nm.newNode();
		Node g = nm.newNode();
		Node h = nm.newNode();
		
		nm.connectNodes(a, b);
		nm.connectNodes(c, d);
		nm.connectNodes(d, e);
		nm.connectNodes(e, f);
		nm.connectNodes(f, g);
		nm.connectNodes(g, h);
		nm.connectNodes(h, a);
		
		nm.connectNodes(a, g);
		nm.connectNodes(c, g);
		nm.connectNodes(c, e);
		
		nm.initiateNeighborIntroductions();
		
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test
	public void neighborIntroduction(){
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		
		Node e = nm.newNode();
		Node f = nm.newNode();
		
		Node g = nm.newNode();
		Node h = nm.newNode();
		Node i = nm.newNode();
		Node j = nm.newNode();
		
		Node k = nm.newNode();
		Node l = nm.newNode();
		
		nm.connectNodes(e, f);
		nm.connectNodes(b, e);
		nm.connectNodes(d, f);
		
		nm.connectNodes(g, a);
		nm.connectNodes(g, b);
		
		nm.connectNodes(h, a);
		nm.connectNodes(h, c);
		
		nm.connectNodes(k, a);
		nm.connectNodes(l, d);
		nm.connectNodes(l, g);
		
		nm.connectNodes(g, i);
		nm.connectNodes(g, d);
		nm.connectNodes(h, j);
		nm.connectNodes(i, a);
		nm.connectNodes(h, c);
		
		nm.connectNodes(a, b);
		nm.connectNodes(a, c);
		nm.connectNodes(a, d);
		
		nm.connectNodes(b, a);
		nm.connectNodes(b, c);
		nm.connectNodes(b, d);
		
		nm.connectNodes(c, a);
		nm.connectNodes(c, b);
		nm.connectNodes(c, d);
		
		nm.connectNodes(d, a);
		nm.connectNodes(d, b);
		nm.connectNodes(d, c);
		
		nm.initiateNeighborIntroductions();
		assertTrue(a.hasNeighbors(b));
		assertTrue(a.hasNeighbors(c));
		assertTrue(a.hasNeighbors(d));
		
		assertTrue(b.hasNeighbors(a));
		assertTrue(b.hasNeighbors(c));
		assertTrue(b.hasNeighbors(d));
		
		assertTrue(c.hasNeighbors(b));
		assertTrue(c.hasNeighbors(a));
		assertTrue(c.hasNeighbors(d));
		
		assertTrue(d.hasNeighbors(b));
		assertTrue(d.hasNeighbors(a));
		assertTrue(d.hasNeighbors(c));
		
		assertFalse(e.hasNeighbors(a));
		assertFalse(e.hasNeighbors(c));
		assertFalse(e.hasNeighbors(d));
		assertFalse(e.hasNeighbors(a,c,d));
		
		nm.printOut();
		System.out.println("\n\n\n");
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotConnectNodeToSelf(){
		Graph nm = new Graph();
		Node a = nm.newNode();
		nm.connectNodes(a, a);
	}
	
	@Test
	public void connectionTest() {
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		
		nm.connectNodes(a, b);
		nm.connectNodes(b, c);
		
		assertTrue(a.hasNeighbor(b));
		assertTrue(b.hasNeighbor(a));
		assertTrue(b.hasNeighbor(c));
		assertTrue(c.hasNeighbor(b));
		assertFalse(a.hasNeighbor(c));
		assertFalse(c.hasNeighbor(a));
	}
	
	@Test
	public void disconnectTest(){
	    
	    int size = 10;
	    
	    for (int i = 5; i<= size; i++){
	        Graph g = new Graph();
	        NodeList l = g.newCompleteGraph(i);
	        assertTrue(g.getEdgeCount() == (i*(i-1)/2));
	        for (int n = 1; n <= i-1; n++){
                assertTrue(l.get(0).hasNeighbor(l.get(n)));
            }
	        g.isolateNodes(l.get(0));
	        int ii = i - 1;
	        assertTrue(g.getEdgeCount() == (ii*(ii-1)/2));
	        for (int n = 1; n <= i-1; n++){
                assertFalse(l.get(0).hasNeighbor(l.get(n)));
            }
	    }
	    
	    
	}
	
	@Test
	public void nodeEqualsNode(){
		Node a = new Node("a",2);
		Node b = new Node("b",2);
		assertTrue(a.equals(b));
	}

	@Test
	public void connectingTwiceOk(){
		Graph nm = new Graph();
		
		Node n1 = nm.newNode();
		Node n2 = nm.newNode();
		
		nm.connectNodes(n1, n2);
		nm.connectNodes(n1, n2);
		
		assertTrue(n1.hasNeighbor(n2));
	}
	
	@Test
	public void testCliquification(){
		Graph nm = new Graph();
		
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		
		Node f = nm.newNode();
		
		nm.connectNodes(a,b,c,d,e);
		
		assertTrue(a.hasNeighbor(b));
		assertTrue(a.hasNeighbor(c));
		assertTrue(a.hasNeighbor(d));
		assertTrue(a.hasNeighbor(e));
		
		assertTrue(b.hasNeighbor(a));
		assertTrue(b.hasNeighbor(c));
		assertTrue(b.hasNeighbor(d));
		assertTrue(b.hasNeighbor(e));
		
		assertTrue(c.hasNeighbor(b));
		assertTrue(c.hasNeighbor(a));
		assertTrue(c.hasNeighbor(d));
		assertTrue(c.hasNeighbor(e));
		
		assertTrue(d.hasNeighbor(b));
		assertTrue(d.hasNeighbor(c));
		assertTrue(d.hasNeighbor(a));
		assertTrue(d.hasNeighbor(e));
		
		assertTrue(e.hasNeighbor(b));
		assertTrue(e.hasNeighbor(c));
		assertTrue(e.hasNeighbor(d));
		assertTrue(e.hasNeighbor(a));
		
		assertFalse(f.hasNeighbor(a));
		assertFalse(f.hasNeighbor(b));
		assertFalse(f.hasNeighbor(c));
		assertFalse(f.hasNeighbor(d));
		assertFalse(f.hasNeighbor(e));
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotConnectNodesAfterCliqueCalcStarted(){
		Graph nm = new Graph();
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		
		nm.connectNodes(a,b,c,d);
		
		nm.initiateNeighborIntroductions();
		nm.connectNodes(e, d);
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotConnectNodesAfterCliqueCalcStarted1(){
		Graph nm = new Graph();
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		
		nm.connectNodes(a,b,c,d);
		
		nm.initiateNeighborIntroductions();
		nm.initiateCliqueCalculation();
		nm.connectNodes(e, d);
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotDisconnectNodesAfterCliqueCalcStarted(){
		Graph nm = new Graph();
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		
		nm.connectNodes(a,b,c,d,e);
		
		nm.initiateNeighborIntroductions();
		nm.disconnectNodes(e, d);
	}
	
	@Test(expected=RuntimeException.class)
	public void cannotDisconnectNodesAfterCliqueCalcStarted1(){
		Graph nm = new Graph();
		Node a = nm.newNode();
		Node b = nm.newNode();
		Node c = nm.newNode();
		Node d = nm.newNode();
		Node e = nm.newNode();
		
		nm.connectNodes(a,b,c,d,e);
		
		nm.initiateNeighborIntroductions();
		nm.initiateCliqueCalculation();
		nm.disconnectNodes(e, d);
	}
	
	@Test
	public void breakOnCliqueFound(){
		Graph nm = new Graph();
		NodeList l10 = nm.newCompleteGraph(10);
		NodeList l20 = nm.newCompleteGraph(20);
		NodeList l50 = nm.newCompleteGraph(50);
		NodeList l30 = nm.newCompleteGraph(30);
		NodeList l40 = nm.newCompleteGraph(40);
		nm.connectNodes(l10.get(0), l10.get(5), l20.get(0), l20.get(5), l30.get(0), l30.get(5), l50.get(0),l50.get(0), l40.get(0), l40.get(5));
		
		nm.setBreakOnCliqueOfSizeFound(50).doCalculation();
		Clique found = nm.getLargestFoundClique();
		nm.printOut();
		System.out.println("\n\n\n");
		assertNotNull(found);
		assertEquals(50, found.size());
	}
}

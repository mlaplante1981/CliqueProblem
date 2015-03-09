package unit;

import static org.junit.Assert.*;
import laplante.michael.clique.Clique;
import laplante.michael.clique.Graph;
import laplante.michael.clique.Node;
import laplante.michael.clique.NodeList;

import org.junit.Test;

public class SharedTwoNodes {

	private Graph g = new Graph();
	
	@Test
	public void test() {
		Node.cliqueCalculationVerbose = true;
		
		
		NodeList l6 = g.newCompleteGraph(6);
		
		NodeList l5a = g.newCompleteGraph(5);
		NodeList l5b = g.newCompleteGraph(5);
		NodeList l5c = g.newCompleteGraph(5);
		NodeList l5d = g.newCompleteGraph(5);
		NodeList l5e = g.newCompleteGraph(5);
		NodeList l5f = g.newCompleteGraph(5);
		
		NodeList t1 = new NodeList();
		NodeList t2 = new NodeList();
		NodeList t3 = new NodeList();
		NodeList t4 = new NodeList();
		NodeList t5 = new NodeList();
		NodeList t6 = new NodeList();
		
		for (int i = 0; i<= 5; i++){
			if (i != 0){
				if (!t1.contains(l6.get(i))){
					t1.add(l6.get(i));
				}
				if (!t1.contains(l5a.get(0))){
					t1.add(l5a.get(0));
				}
				g.connectNodes(l6.get(i),l5a.get(0));
			}
			if (i != 1){
				if (!t2.contains(l6.get(i))){
					t2.add(l6.get(i));
				}
				if (!t2.contains(l5b.get(0))){
					t2.add(l5b.get(0));
				}
				g.connectNodes(l6.get(i),l5b.get(0));
			}
			if (i != 2){
				if (!t3.contains(l6.get(i))){
					t3.add(l6.get(i));
				}
				if (!t3.contains(l5c.get(0))){
					t3.add(l5c.get(0));
				}
				g.connectNodes(l6.get(i),l5c.get(0));
			}
			if (i != 3){
				if (!t4.contains(l6.get(i))){
					t4.add(l6.get(i));
				}
				if (!t4.contains(l5d.get(0))){
					t4.add(l5d.get(0));
				}
				g.connectNodes(l6.get(i),l5d.get(0));
			}
			if (i != 4){
				if (!t5.contains(l6.get(i))){
					t5.add(l6.get(i));
				}
				if (!t5.contains(l5e.get(0))){
					t5.add(l5e.get(0));
				}
				g.connectNodes(l6.get(i),l5e.get(0));
			}
			if (i != 5){
				if (!t6.contains(l6.get(i))){
					t6.add(l6.get(i));
				}
				if (!t6.contains(l5f.get(0))){
					t6.add(l5f.get(0));
				}
				g.connectNodes(l6.get(i),l5f.get(0));
			}
		}
		
		connectEdgeCliquesToOtherEdge(l5a, l5b);
		connectEdgeCliquesToOtherEdge(l5b, l5c);
		connectEdgeCliquesToOtherEdge(l5c, l5d);
		connectEdgeCliquesToOtherEdge(l5d, l5e);
		connectEdgeCliquesToOtherEdge(l5e, l5f);
		connectEdgeCliquesToOtherEdge(l5f, l5a);

		g.doCalculation();
		g.printOut();
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l6));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5a));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5b));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5c));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5d));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5e));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l5f));
		
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t1));
		t1.add(l6.get(0));
		assertFalse(Clique.testEachNodeForIfInCliqueWithEachOther(t1));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t2));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t3));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t4));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t5));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(t6));
	}

	private void connectEdgeCliquesToOtherEdge(NodeList l1, NodeList l2){
		for (int i = 0; i<= 4; i++){
			if (i != 0){
				g.connectNodes(l1.get(i),l2.get(0));
			}
			if (i != 1){
				g.connectNodes(l1.get(i),l2.get(0));
			}
			if (i != 2){
				g.connectNodes(l1.get(i),l2.get(0));
			}
			if (i != 3){
				g.connectNodes(l1.get(i),l2.get(0));
			}
			if (i != 4){
				g.connectNodes(l1.get(i),l2.get(0));
			}
		}
	}
	
}

package unit;

import static org.junit.Assert.*;
import laplante.michael.clique.Clique;
import laplante.michael.clique.Node;
import laplante.michael.clique.NodeList;
import laplante.michael.clique.Graph;

import org.junit.Test;

public class TestNode {

	@Test
	public void testNeighborIntroduction() {
		Graph nm = new Graph();
		NodeList l = nm.newCompleteGraph(5);
		nm.initiateNeighborIntroductions();
		assertTrue(l.get(0).hasNeighbor(l.get(1)));
		assertTrue(l.get(0).hasNeighbor(l.get(2)));
		assertTrue(l.get(0).hasNeighbor(l.get(3)));
		assertTrue(l.get(0).hasNeighbor(l.get(4)));
	}

	@Test
	public void testCalculateCliques() {
		Graph nm = new Graph();
		NodeList l = nm.newCompleteGraph(5);
		nm.doCalculation();
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l));
	}

	@Test
	public void testGetCliquesSharedWithNode() {
		Node.cliqueCalculationVerbose = true;
		Graph nm = new Graph();
		NodeList l2 = nm.newCompleteGraph(2);
		NodeList l5a = nm.newCompleteGraph(5);
		NodeList l5b = nm.newCompleteGraph(5);
		nm.cliquify(nm.getListOfNodesFromListOfNodes(l2,l5a));
		nm.cliquify(nm.getListOfNodesFromListOfNodes(l2,l5b));
		nm.doCalculation();
		nm.printOut();
		l2.get(0).getCliquesSharedWithNode(l2.get(1));
		assertTrue(nm.getLargestFoundClique().size()==7);
	}


}

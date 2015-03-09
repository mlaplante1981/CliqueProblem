package unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import laplante.michael.clique.Clique;
import laplante.michael.clique.Node;
import laplante.michael.clique.NodeList;
import laplante.michael.clique.Graph;

import org.junit.Before;
import org.junit.Test;

public class FindCliquesOfSizes {

	@Before
	public void unsetVerboseOutput(){
		Node.cliqueCalculationVerbose = false;
		Node.neighborIntroVerbose = false;
	}
	
	@Test
	public void onlyCompleteGraphs() {
		for (int i = 4; i<=50; i++){
			Graph g = new Graph();//.setBreakOnCliqueOfSizeFound(i);
			g.newCompleteGraph(i);
			g.doCalculation();
			assertTrue(String.format("Did not get clique of size %d",i),g.getLargestFoundClique().size() == i);
			g.printOut();
			System.out.println("\n\n");
		}
	}
	
	@Test
	public void otherNodesWithTheCompleteGraph() {
		for (int i = 8; i<=50; i++){
			Graph g = new Graph();
			NodeList l1 = g.newCompleteGraph(i-1);
			NodeList l2 = g.newCompleteGraph(i-2);
			NodeList ofSize = g.newCompleteGraph(i);
			NodeList l3 = g.newCompleteGraph(i-3);
			NodeList l4 = g.newCompleteGraph(i-4);
			
			g.connectNodes(l1.get(0),l2.get(0),l3.get(0),l4.get(0),ofSize.get(0),ofSize.get(i-1),ofSize.get(i-3));
			
			g.doCalculation();
			assertTrue(String.format("Did not get clique of size %d",i),g.getLargestFoundClique().size() >= i);
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l2));
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l3));
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l4));
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(ofSize));
			g.printOut();
			System.out.println("\n\n");
		}
	}
	
	@Test
	public void otherNodesWithTheCompleteGraphMoreComplicated() {
		for (int i = 4; i<=50; i++){
			Graph g = new Graph().setBreakOnCliqueOfSizeFound(i);
			int w = 175;
			int h = 175;
			
			Node[][] nodes = new Node[h+1][w+1]; 
			
			for (int y = 0; y <= h; y++){
				for (int x = 0; x <= w; x++){
					nodes[y][x] = g.newNode();
				}	
			}

			for (int y = 0; y <= h-1; y++){
				for (int x = 0; x <= w-1; x++){
					Node a = nodes[y][x];
					Node b = nodes[y][x+1];
					Node c = nodes[y+1][x];
					Node d = nodes[y+1][x+1];
					g.connectNodes(a,b,c,d);
				}	
			}
			
			NodeList l1 = g.newCompleteGraph(i-2);
			NodeList l2 = g.newCompleteGraph(i-2);
			NodeList ofSize = g.newCompleteGraph(i+3);
			NodeList l3 = g.newCompleteGraph(i-1);
			NodeList l4 = g.newCompleteGraph(i-1);
			
			for (int y = 0; y < h-1; y+=h/3){
				for (int x = 0; x < w-1; x+=w/3){
					Node a = nodes[y][x];
					Node b = nodes[y+1][x];
					Node c = nodes[y][x+1];
					g.connectNodes(a,b,c,l1.get(0),l2.get(0),l3.get(0),l4.get(0),ofSize.get(0));
				}	
			}
			
			g.connectNodes(l1.get(0),l2.get(0),l3.get(0),l4.get(0),ofSize.get(0),ofSize.get(i-1),ofSize.get(i-3));
			
			g.doCalculation();
//			g.printOut();
			assertTrue(String.format("Did not get clique of size %d",i),g.getLargestFoundClique().size() >= i);
//			nm.printOut();
			System.out.println("\n\n");
		}
	}
	
	@Test
	public void otherNodesWithTheCompleteGraphWithDisconnectedSubGraphs() {
		for (int i = 5; i<=50; i++){
			Graph g = new Graph().setBreakOnCliqueOfSizeFound(i);
			int w = 50;
			int h = 50;
			
			Node[][] nodes = new Node[h+1][w+1]; 
			
			for (int y = 0; y <= h; y++){
				for (int x = 0; x <= w; x++){
					nodes[y][x] = g.newNode();
				}	
			}

			for (int y = 0; y <= h-1; y++){
				for (int x = 0; x <= w-1; x++){
					Node a = nodes[y][x];
					Node b = nodes[y][x+1];
					Node c = nodes[y+1][x];
					Node d = nodes[y+1][x+1];
					g.connectNodes(a,b,c,d);
				}	
			}
			NodeList l1 = g.newCompleteGraph(i-2);
			NodeList l2 = g.newCompleteGraph(i-2);
			NodeList ofSize = g.newCompleteGraph(i+1);
			NodeList l3 = g.newCompleteGraph(i-2);
			NodeList l4 = g.newCompleteGraph(i-2);
			
			g.connectNodes(l1.get(0),l2.get(0),l3.get(0),l4.get(0),ofSize.get(0),ofSize.get(i-1),ofSize.get(i-3));
			
			g.doCalculation();
			assertTrue(String.format("Did not get clique of size %d",i),g.getLargestFoundClique().size() >= i);
//			g.printOut();
//			nm.printOut();
			System.out.println("\n\n");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void aReasonablyComplexGraph(){
		Graph g = new Graph().setBreakOnCliqueOfSizeFound(49);
		
		NodeList c10 = g.newCompleteGraph(10);
		NodeList c15 = g.newCompleteGraph(15);
		NodeList c20 = g.newCompleteGraph(20);
		NodeList c25 = g.newCompleteGraph(25);
		NodeList c30 = g.newCompleteGraph(30);
		NodeList c35 = g.newCompleteGraph(35);
		NodeList c40 = g.newCompleteGraph(40);
		NodeList c45 = g.newCompleteGraph(45);
		NodeList c50 = g.newCompleteGraph(50);
		
		g.connectNodes(c10.subList(0, 4),c15.subList(0, 4));
		g.connectNodes(c15.subList(0, 4),c20.subList(0, 4));
		g.connectNodes(c20.subList(0, 4),c25.subList(0, 4));
		g.connectNodes(c25.subList(0, 4),c30.subList(0, 4));
		g.connectNodes(c30.subList(0, 4),c35.subList(0, 4));
		g.connectNodes(c35.subList(0, 4),c40.subList(0, 4));
		g.connectNodes(c50.subList(0, 4),c15.subList(5, 9));
		g.connectNodes(c50.subList(5, 9),c25.subList(5, 9));
		g.connectNodes(c50.subList(10, 14),c35.subList(5, 9));
		g.connectNodes(c50.subList(15, 19),c45.subList(5, 9));
		
		g.doCalculation();
//		g.printOut();
		assertTrue("Did not get a clique of 49 or larger", g.getLargestFoundClique().size() >= 49);
		
	}
	
	@Test
	public void cliqueOf50SetAmongstTheBunchOf4Cliques() {
		int i = 50;
		Graph g = new Graph();//.setBreakOnCliqueOfSizeFound(i);
		int w = 200;
		int h = 200;
		
		Node[][] nodes = new Node[h+1][w+1]; 
		
		for (int y = 0; y <= h; y++){
			for (int x = 0; x <= w; x++){
				nodes[y][x] = g.newNode();
			}	
		}

		for (int y = 0; y <= h-1; y++){
			for (int x = 0; x <= w-1; x++){
				Node a = nodes[y][x];
				Node b = nodes[y][x+1];
				Node c = nodes[y+1][x];
				Node d = nodes[y+1][x+1];
				g.connectNodes(a,b,c,d);
			}	
		}
		int c = 0;
		ArrayList<Node> cliqueOf50 = new ArrayList<>();
		for (int y = 5; y <= h-1; y+=h/10){
			for (int x = 5; x <= w-1; x+=h/5){
				cliqueOf50.add(nodes[y][x]);
				c++;
			}
		}
		assertEquals("Woops, didn't make a 50 clique", c,i);
		g.cliquify(cliqueOf50);
		g.doCalculation();
//		g.printOut();
		assertTrue(String.format("Did not get clique of size %d",i),g.getLargestFoundClique().size() >= i);

		System.out.println("\n\n");
	}

}

package unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import laplante.michael.clique.Clique;
import laplante.michael.clique.Graph;
import laplante.michael.clique.Node;
import laplante.michael.clique.NodeList;

import org.junit.Test;

public class DesignedGraphs {

	@Test
	public void A() {
		Graph gr = new Graph();
		
		ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		
		// Central cliques
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		nodeLists.add(gr.connectNodes(a,b,c,d,e));
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		Node h = gr.newNode();
		nodeLists.add(gr.connectNodes(f,g,h));
		
		Node i = gr.newNode();
		Node j = gr.newNode();
		Node k = gr.newNode();
		Node l = gr.newNode();
		nodeLists.add(gr.connectNodes(i,j,k,l));
		
		Node m = gr.newNode();
		Node n = gr.newNode();
		Node o = gr.newNode();
		nodeLists.add(gr.connectNodes(m,n,o));
		
		Node p = gr.newNode();
		Node q = gr.newNode();
		Node r = gr.newNode();
		Node s = gr.newNode();
		nodeLists.add(gr.connectNodes(p,q,r,s));
		
		Node t = gr.newNode();
		Node u = gr.newNode();
		Node v = gr.newNode();
		Node w = gr.newNode();
		nodeLists.add(gr.connectNodes(t,u,v,w));
		
		// Connect outer cliques to center clique
		nodeLists.add(gr.connectNodes(a,e,f,g));
		nodeLists.add(gr.connectNodes(a,b,i,j));
		nodeLists.add(gr.connectNodes(b,c,m,n));
		nodeLists.add(gr.connectNodes(c,d,p,q));
		nodeLists.add(gr.connectNodes(d,e,t,u));
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		for (NodeList list: nodeLists){
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(list));
		}
	}

	@Test
	public void B() {
		Graph gr = new Graph();
		
		ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		
		// Central cliques
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		nodeLists.add(gr.connectNodes(a,b,c,d,e));
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		Node h = gr.newNode();
		nodeLists.add(gr.connectNodes(f,g,h));
		
		Node i = gr.newNode();
		Node j = gr.newNode();
		Node k = gr.newNode();
		Node l = gr.newNode();
		nodeLists.add(gr.connectNodes(i,j,k,l));
		
		Node m = gr.newNode();
		Node n = gr.newNode();
		Node o = gr.newNode();
		nodeLists.add(gr.connectNodes(m,n,o));
		
		Node p = gr.newNode();
		Node q = gr.newNode();
		Node r = gr.newNode();
		Node s = gr.newNode();
		nodeLists.add(gr.connectNodes(p,q,r,s));
		
		Node t = gr.newNode();
		Node u = gr.newNode();
		Node v = gr.newNode();
		Node w = gr.newNode();
		nodeLists.add(gr.connectNodes(t,u,v,w));
		
		// Connect outer cliques to center clique
		nodeLists.add(gr.connectNodes(a,e,f,g));
		nodeLists.add(gr.connectNodes(a,b,i,j));
		nodeLists.add(gr.connectNodes(b,c,m,n));
		nodeLists.add(gr.connectNodes(c,d,p,q));
		nodeLists.add(gr.connectNodes(d,e,t,u));
		
		// Outer ring
		nodeLists.add(gr.connectNodes(h,g,i,k));
		nodeLists.add(gr.connectNodes(l,j,m,o));
		nodeLists.add(gr.connectNodes(o,n,p,r));
		nodeLists.add(gr.connectNodes(q,s,u,v));
		nodeLists.add(gr.connectNodes(t,w,f,h));
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		for (NodeList list: nodeLists){
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(list));
		}
	}
	
	@Test
	public void C(){
		Graph gr = new Graph();
		
		ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		
		// Central cliques
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		nodeLists.add(gr.connectNodes(a,b,c,d,e));
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		Node h = gr.newNode();
		nodeLists.add(gr.connectNodes(f,g,h));
		
		Node i = gr.newNode();
		Node j = gr.newNode();
		Node k = gr.newNode();
		Node l = gr.newNode();
		nodeLists.add(gr.connectNodes(i,j,k,l));
		
		Node m = gr.newNode();
		Node n = gr.newNode();
		Node o = gr.newNode();
		nodeLists.add(gr.connectNodes(m,n,o));
		
		Node p = gr.newNode();
		Node q = gr.newNode();
		Node r = gr.newNode();
		Node s = gr.newNode();
		nodeLists.add(gr.connectNodes(p,q,r,s));
		
		Node t = gr.newNode();
		Node u = gr.newNode();
		Node v = gr.newNode();
		Node w = gr.newNode();
		nodeLists.add(gr.connectNodes(t,u,v,w));
		
		// Connect outer cliques to center clique
		nodeLists.add(gr.connectNodes(a,e,f,g));
		nodeLists.add(gr.connectNodes(a,b,i,j));
		nodeLists.add(gr.connectNodes(b,c,m,n));
		nodeLists.add(gr.connectNodes(c,d,p,q));
		nodeLists.add(gr.connectNodes(d,e,t,u));
		
		// Outer ring
		nodeLists.add(gr.connectNodes(h,g,i,k));
		nodeLists.add(gr.connectNodes(l,j,m,o));
		nodeLists.add(gr.connectNodes(o,n,p,r));
		nodeLists.add(gr.connectNodes(q,s,u,v));
		nodeLists.add(gr.connectNodes(t,w,f,h));
		
		nodeLists.add(gr.connectNodes(t,a,b));
		nodeLists.add(gr.connectNodes(g,b,c));
		nodeLists.add(gr.connectNodes(j,c,d));
		nodeLists.add(gr.connectNodes(n,d,e));
		nodeLists.add(gr.connectNodes(q,e,a));
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		for (NodeList list: nodeLists){
			assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(list));
		}
	}
	
	@Test
	public void D(){
		Graph gr = new Graph();
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		NodeList l1 = gr.connectNodes(a,b,c,d,e);
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		NodeList l2 = gr.connectNodes(a,e,g,f);
		
		Node h = gr.newNode();
		Node i = gr.newNode();
		NodeList l3 = gr.connectNodes(d, e, h, i);
		
		NodeList l4 = gr.connectNodes(c,h,i,a); // hehe, like a chia pet... i need more sleep
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l2));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l3));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l4));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(c,h,i,a,d,e));
	}
	
	@Test
	public void E(){
		Graph gr = new Graph();
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		NodeList l1 = gr.connectNodes(a,b,c,d,e);
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		NodeList l2 = gr.connectNodes(a,e,g,f);
		
		Node h = gr.newNode();
		Node i = gr.newNode();
		NodeList l3 = gr.connectNodes(d, e, h, i);
		
		gr.connectNodes(c,h);
		NodeList l4 = gr.connectNodes(a,h,i);
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
		l1.add(f);
		assertFalse(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l2));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l3));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l4));
		assertTrue(d.isInCliqueWith(c,h));
	}
	
	@Test
	public void F(){
		Graph gr = new Graph();
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		Node e = gr.newNode();
		NodeList l1 = gr.connectNodes(a,b,c,d,e);
		
		Node f = gr.newNode();
		Node g = gr.newNode();
		NodeList l2 = gr.connectNodes(a,e,g,f);
		
		Node h = gr.newNode();
		Node i = gr.newNode();
		Node j = gr.newNode();
		Node k = gr.newNode();
		Node l = gr.newNode();
		NodeList l3 = gr.connectNodes(h,i,j,k,l,b,f);
		
		NodeList l4 = gr.connectNodes(b,h,a,g);
		
		gr.connectNodes(b,g);
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
		l1.add(f);
		assertFalse(Clique.testEachNodeForIfInCliqueWithEachOther(l1));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l2));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l3));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(b,g,a));
		assertTrue(Clique.testEachNodeForIfInCliqueWithEachOther(l4));
	}
	
	@Test
	public void G(){
		Graph gr = new Graph();
		
		Node a = gr.newNode();
		Node b = gr.newNode();
		Node c = gr.newNode();
		Node d = gr.newNode();
		
		Node e = gr.newNode();
		Node f = gr.newNode();
		Node g = gr.newNode();
		Node h = gr.newNode();
		
		Node i = gr.newNode();
		Node j = gr.newNode();
		Node k = gr.newNode();
		
		NodeList l1 = gr.newCompleteGraph(5);
		
		gr.connectNodes(a,b,c,d);
		gr.connectNodes(e,f,g,h);
		gr.connectNodes(i,j,k);
		gr.connectNodes(d,e);
		gr.connectNodes(e,i);
		gr.connectNodes(l1.get(0),a);
		gr.connectNodes(l1.get(0),k);
		
		gr.doCalculation();
		gr.printNodeNeighborhoodData();
		gr.printOut();
		
		for (Clique cl: gr.getCliquesFound()){
            System.out.println(cl);
        }
	}
	
	@Test
	public void H(){
		Graph gr = new Graph();
		ArrayList<NodeList> nodeLists = new ArrayList<NodeList>();
		for (int i = 1; i<= 50; i++){
			nodeLists.add(gr.newNodes(3));
		}
		for (NodeList l: nodeLists){
			int ind = 1;
			for (NodeList ll: nodeLists){
				if (l != ll){
					for (int i = 0; i<=l.size()-1;i++){
						for (int ii = 0; ii<=ll.size()-1;ii++){
							if (i==2 || ind % 5 == 0 || i != ii){
								gr.connectNodes(l.get(i), ll.get(ii));
							}
						}
					}
				}
				ind++;
			}
		}
		gr.doCalculation();
		gr.printOut();
	}
}

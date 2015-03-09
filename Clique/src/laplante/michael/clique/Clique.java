/*
 * The MIT License (MIT)

Copyright (c) 2015 Michael LaPlante

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package laplante.michael.clique;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A complete graph which itself is a subgraph, or part of a larger graph. Each node in this clique "knows about" or is connected to each other node.
 * This class is immutable for efficiency purposes. 
 * 
 * @author Michael LaPlante
 *
 */
//TODO this class could just simply extend HashSet<Node> and implement comparable and do away with the equals() and hashCode() methods
public class Clique implements Comparable<Clique>{

	private SortedSet<Node> nodes = new TreeSet<Node>();
	private int hashCode;
	
	public Clique(Node ... nodes){
		for (Node node: nodes){
			this.nodes.add(node);
		}
		this.hashCode = this.nodes.hashCode();
	}
	
	public Clique(SortedSet<Node> nodeSet, Node...nodes) {
		this.nodes.addAll(nodeSet);
		for (Node n: nodes){
			this.nodes.add(n);
		}
		this.hashCode = this.nodes.hashCode();
	}
	
	public Node[] getNodesExcluding(Node...nodesToExclude){
		Node[] r = new Node[0];
		for (Node n: nodes){
			for (Node ex: nodesToExclude){
				if (!n.equals(ex)){
					r = addNodeToArray(r, n);
				}
			}
		}
		return r;
	}
	
	private Node[] addNodeToArray(Node[] nodes, Node n){
		Node[] newNodes = new Node[nodes.length+1];
		int i = -1;
		for (i = 0; i<= nodes.length-1; i++){
			newNodes[i] = nodes[i];
		}
		newNodes[newNodes.length-1] = n;
		return newNodes;
	}
	
	/**
	 * Creates a new Clique merging the passed list of cliques and excluding Nodes in the the passed list of nodes.
	 * @param cliques Cliques to merge together
	 * @param excludeNodes Nodes to exclude from the new Clique
	 */
	public Clique(List<Clique> cliques, Set<Node> excludeNodes){
		for (Clique c: cliques){
			for (Node n: c.nodes){
				if (!excludeNodes.contains(n)){
					this.nodes.add(n);
				}
			}
		}
		this.hashCode = this.nodes.hashCode();
	}
	
	public Clique(List<Clique> cliques) {
		for (Clique c: cliques){
			for (Node n: c.nodes){
				this.nodes.add(n);
			}
		}
		this.hashCode = this.nodes.hashCode();
	}

	public Clique(Set<Node> cliques) {
		for (Node n: cliques){
			this.nodes.add(n);
		}
		this.hashCode = this.nodes.hashCode();
	}
	
	public Clique(CliqueBuilder cliqueBuilder) {
		for (Node n: cliqueBuilder.nodes){
			this.nodes.add(n);
		}
		this.hashCode = this.nodes.hashCode();
	}

	public static class CliqueBuilder{
		private ArrayList<Node> nodes = new ArrayList<Node>();
		
		public CliqueBuilder(){
			
		}
		
		public CliqueBuilder addNodes(Node...nodes){
			for (Node n: nodes){
				this.nodes.add(n);
			}
			return this;
		}
		
		public CliqueBuilder addNodes(Collection<Node> nodes){
			for (Node n: nodes){
				this.nodes.add(n);
			}
			return this;
		}
		
		public Clique build(){
			return new Clique(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Clique){
			return ((Clique) o).nodes.equals(this.nodes);
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString(){
		return String.format("%d - %s",this.nodes.size(),this.nodes.toString());
	}
	
	public int size(){
		return this.nodes.size();
	}
	
	public boolean hasNodes(Node ... nodes){
		boolean hasthem = true;
		for (Node node: nodes){
			if (!this.nodes.contains(node)){
				hasthem = false;
				break;
			}
		}
		return hasthem;
	}
	
//	public List<Node> whichNodesInClique(Node ... nodes){
//		ArrayList<Node> n = new ArrayList<>();
//		for (Node node: nodes){
//			if (this.nodes.contains(node)){
//				n.add(node);
//			}
//		}
//		return n;
//	}
//	
//	public List<Node> whichNodesNotInClique(Node ... nodes){
//		ArrayList<Node> n = new ArrayList<>();
//		for (Node node: nodes){
//			if (!this.nodes.contains(node)){
//				n.add(node);
//			}
//		}
//		return n;
//	}
	
//	public void addNodes(Set<Node> nodes){
//		this.nodes.addAll(nodes);
//	}
//	
//	public void addNodes(Node... nodes){
//		for (Node n: nodes){
//			this.nodes.add(n);
//		}
//	}
//	
//	public void removeNodes(Node...nodes){
//		for (Node node: nodes){
//			this.nodes.remove(node);
//		}
//	}
//
	public SortedSet<Node> getNodes() {
		return nodes;
	}
	
	@Override
	public int compareTo(Clique o) {
		return Integer.compare(o.size(), this.size());
	}
	
	public static boolean testEachNodeForIfInCliqueWithEachOther(NodeList l){
		for (int i = 0; i<= l.size()-1;i++){
			Node a = l.get(i);
			Node[] b = new Node[l.size()-1];
			int ii = 0;
			for (Node n: l){
				if (n != a){
					b[ii] = n;
					ii++;
				}
			}
			if (!a.isInCliqueWith(b)){
				return false;
			}
		}
		return true;
	}

	public static boolean testEachNodeForIfInCliqueWithEachOther(Node...nodes) {
		for (int i = 0; i<= nodes.length-1;i++){
			Node a = nodes[i];
			Node[] b = new Node[nodes.length-1];
			int ii = 0;
			for (Node n: nodes){
				if (n != a){
					b[ii] = n;
					ii++;
				}
			}
			if (!a.isInCliqueWith(b)){
				return false;
			}
		}
		return true;
	}
	
}

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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Manager of nodes: Owns a graph and all the functions to build the graph including
 * <ul>
 * <li> adding nodes </li>
 * <li> connecting nodes together</li>
 * <li> disconnecting two nodes</li>
 * <li> telling each of the nodes to start calculating the cliques</li>
 * </ul>
 * @author Michael LaPlante
 *
 */
public class Graph {

	/**
	 * List of nodes this manager manages.
	 */
	private SortedSet<Node> nodes = new TreeSet<Node>();
	
	private boolean cliqueCalculationsStarted = false;
	
	private int idIndex = 0;
	
	private long totalCalculationTime;
	private long neighborIntroductionTime;
	private long cliqueCalcTime;
	
	private boolean breakOnCliqueFound = false;
	private int lookingForCliqueSize;
	
	private Clique largestFoundClique;
	private Set<Clique> cliquesFound = new HashSet<Clique>();
	private boolean okToBreak = false;
	
	private int edgeCount;
	
	public Graph(){

	}
	
	/**
	 * Generates a complement graph of this graph.
	 * @return A new graph, that is a complement of this graph.
	 */
	public Graph getComplement(){
		// TODO add ability to generate a complement graph
		Graph g = new Graph();
		for (int i = 1; i<=nodes.size(); i++){
			g.newNode();
		}
		for (Node n: nodes){
			for (Node nn: nodes){
				if (n!=nn){
					if (!n.hasNeighbor(nn)){
					}
				}
			}
		}
		return g;
	}
	
	/**
	 * Convenience method for creating a node when you don't want to specify a name. A name is generated for you.
	 * @return
	 */
	public Node newNode(){
		int i = idIndex+1;
		Node n = newNode(getNodeName(i));
		n.setNodeManager(this);
		return n;
	}

	/**
	 * Creates a node named with the given name.
	 * @param name Name of the node
	 * @return
	 */
	public Node newNode(String name){
		Node n = new Node(name,idIndex);
		idIndex++;
		nodes.add(n);
		return n;
	}
	
	/**
	 * Builds an alphabetic name based on the ID passed. If id > 26 the alphabet characters are reused. For example 30 = AD 
	 * @param id The ID of the node to generate a name for.
	 * @return
	 */
	private String getNodeName(int id){
		StringBuilder sb = new StringBuilder();
        int n = id - 1;
        while (n >= 0) {
            int alphabetCharacter = (n % 26)  + 65;
            sb.append((char)alphabetCharacter);
            n = (n  / 26) - 1;
        }
        return sb.reverse().toString();
	}
	
	/**
	 * Connects two nodes together. This cannot be called after neighborIntroductions. After connecting two nodes together,
	 * each node will have the other in its list of neighbor nodes.
	 * @param a Node a to connect to node b.
	 * @param b Node b to connect to node a.
	 */
	public void connectNodes(Node a, Node b){
		if (cliqueCalculationsStarted){
			throw new RuntimeException("Cannot modify node connections after introductions have been made.");
		}
		if (!a.hasNeighbor(b)){
			a.setNeighbor(b);
			b.setNeighbor(a);
			edgeCount++;
		}
	}
	
	/**
	 * Disconnects two nodes. This cannot be called after neighborIntroductions.
	 * @param a Node a to disconnect from node b.
	 * @param b Node b to disconnect from node a.
	 */
	public void disconnectNodes(Node a, Node b){
		if (cliqueCalculationsStarted){
			throw new RuntimeException("Cannot modify node connections after introductions have been made.");
		}
		if (a.hasNeighbor(b)){
			a.unsetNeighbor(b);
			b.unsetNeighbor(a);
			edgeCount--;
		}
	}
	
	/**
	 * Disconnects all of the nodes passed from eachother.
	 * @param nodes List of nodes, each of which will be disconnected from eachother. 
	 */
	public void disconnectNodes(Node... nodes){
		for (Node n: nodes){
			for (Node nn: nodes){
				disconnectNodes(n, nn);
			}
		}
	}
	
	/**
	 * Removes all connections each from of the nodes passed, has. After calling, each of the nodes will have no neighbors. 
	 * @param nodes
	 */
	public void isolateNodes(Node...nodes){
	    for (Node n: nodes){
	        edgeCount -= n.isolate();
	    }
	}
	
	/**
	 * Convenience method to create a clique (complete graph) of the given nodes. 
	 * @param nodes Nodes to create a clique out of
	 * @return A new NodeList object for convenience use
	 */
	public NodeList connectNodes(Node...nodes){
		NodeList l = new NodeList();
		for (Node n: nodes){
			l.add(n);
			for (Node nn: nodes){
				if (!n.equals(nn)){
					connectNodes(n, nn);
					
				}
			}
		}
		return l;
	}

	/**
	 * Convenience method (with a funny name) to create a clique (complete graph) of the given nodes. 
	 * @param nodes Nodes to create a clique out of
	 */
	public void cliquify(List<Node> nodes){
		for (Node n: nodes){
			for (Node nn: nodes){
				if (!n.equals(nn)){
					connectNodes(n, nn);
				}
			}
		}
	}
	
	public void cliquify(ArrayList<Node> nodeList, Node...nodes) {
		for (Node n: nodeList){
			for (Node nn: nodeList){
				if (!n.equals(nn)){
					connectNodes(n, nn);
				}
			}
			for (Node nn: nodes){
				if (!n.equals(nn)){
					connectNodes(n, nn);
				}
			}
		}
		for (Node n: nodes){
			for (Node nn: nodeList){
				if (!n.equals(nn)){
					connectNodes(n, nn);
				}
			}
			for (Node nn: nodes){
				if (!n.equals(nn)){
					connectNodes(n, nn);
				}
			}
		}
	}
	
	/**
	 * Convenience method for calculating the cliques in one call instead of two separate calls (initiateNeighborIntroductions() and calculateCliques()).
	 */
	public void doCalculation(){
		if (this.lookingForCliqueSize == 0){
			this.lookingForCliqueSize = nodes.size();
		}
		System.out.println(String.format("Node Count: %d Edge Count: %d",this.nodes.size(),this.edgeCount));
		System.out.printf("Starting neighbor introductions\n");
		long startTime = System.currentTimeMillis();
		initiateNeighborIntroductions();
		System.out.printf("Neighbor Intro Count: %d\n",Node.neighborIntroCounter);
		System.out.printf("Starting clique calculations\n");
		initiateCliqueCalculation();
		long endTime = System.currentTimeMillis();
		this.totalCalculationTime = endTime - startTime;
		System.out.printf("Subgraph calculation process finished in %d ms\n",this.totalCalculationTime);
		System.out.printf("Cliques found: %d Largest Clique Found Size: %d\n", this.cliquesFound.size(), this.largestFoundClique == null ? 0 : this.largestFoundClique.size());
		System.out.printf("Largest Clique Found: %s\n", this.largestFoundClique);
	}
	
	/**
	 * The first step in the clique calculation process. Each node goes to each of its own
	 * neighbors and advertises the list of neighbors it has. After this method is called,
	 * each node will know each of the 3 cliques that it belongs to. 
	 */
	public void initiateNeighborIntroductions(){
//		NodesTo3CliqueMap.instance.clear(); // Because this is a singleton, the previous listing of neighbors must be cleared else wrong results will come if a second execution is done!
		// CLEANUP
		// Do any cleanup necessary before starting a second calculation here
		this.largestFoundClique = null;
		this.cliquesFound.clear();
		this.okToBreak = false;
		// END CLEANUP
		long startTime = System.currentTimeMillis();
		if (!cliqueCalculationsStarted){
			cliqueCalculationsStarted = true;
			for (Node n: nodes){
				n.introduceNeighbors();
			}
		}
		long endTime = System.currentTimeMillis();
		this.neighborIntroductionTime = endTime - startTime;
		System.out.printf("%d Neighbor Introductions finished in %d ms\n",Node.neighborIntroCounter, this.neighborIntroductionTime);
	}
	
	/**
	 * The final step in the calculation process. Goes to each node to have them calculate 
	 * the cliques of which they are apart.
	 * 
	 * See Node.calculateCliques() for details.
	 */
	//TODO Sort nodes by neighbor count in descending order because nodes with more neighbors are more likely to have larger cliques 
	public void initiateCliqueCalculation(){
		long startTime = System.currentTimeMillis();
		for (Node n: nodes){
			n.calculateCliques();
			if (this.okToBreak){
				System.out.printf("Found clique %s\n",this.largestFoundClique);
				break;
			}
		}
		long endTime = System.currentTimeMillis();
		this.cliqueCalcTime = endTime - startTime; 
		System.out.printf("Clique Calculation finished in %d ms\n",this.cliqueCalcTime);
	}

	/**
	 * Shows the output of each node's name, ID, neighbor count, neighbor list and clique list.
	 */
	public void printOut() {
		for (Node n: nodes){
			System.out.println(String.format("%3s: %-5d\t%3d\t%-25s", 
					n, 
					n.getId(),
					n.neighborCount(),
//					n.neighbors(),
//					n.mutualNeighbors(),
					n.getDistinctCliquesBySize()
				));
		}
		
	}
	
	public void printNodeNeighborhoodData(){
		for (Node n: nodes){
			n.printOutNeighborhoodData();
		}
	}
	
	/**
	 * Creates a new complete graph of the given size and returns it.
	 * @param size
	 * @return
	 */
	public NodeList newCompleteGraph(int size) {
		if (size <=0){
			throw new RuntimeException(String.format("Cannot create a complete graph of size %d",size));
		}
		NodeList nodes = new NodeList();
		for (int i = 1; i<=size; i++){
			nodes.add(newNode());
		}
		cliquify(nodes);
		return nodes;
	}
	
	public NodeList newNodes(int count){
		if (count <=0){
			throw new RuntimeException(String.format("Cannot create a count of %d nodes. You must supply a positive number greater than 1!",count));
		}
		NodeList nodes = new NodeList();
		for (int i = 1; i<=count; i++){
			nodes.add(newNode());
		}
		return nodes;
	}
	
	public Graph setBreakOnCliqueOfSizeFound(int size){
		this.breakOnCliqueFound = true;
		this.lookingForCliqueSize = size;
		return this;
	}
	
	public Graph setLookingForCliqueOfSize(int size){
		this.lookingForCliqueSize = size;
		return this;
	}
	
	public Graph shouldHaltOnCliqueFound(boolean halt){
		this.breakOnCliqueFound = true;
		return this;
	}
	
	public boolean reportCliqueFound(Clique c){
		this.cliquesFound.add(c);
		if (this.largestFoundClique == null || c.size() > this.largestFoundClique.size()){
			this.largestFoundClique = c;
		}
		if (c.size() >= this.lookingForCliqueSize){
			this.largestFoundClique = c;
			if (!this.breakOnCliqueFound){
				return false;
			}else{
				this.okToBreak = true;
				return true;
			}
		}else{
			return false;
		}
	}

	public Clique getLargestFoundClique() {
		return largestFoundClique;
	}

	public void disconnectNodes(NodeList incompat3) {
		for (Node n: incompat3){
			for (Node nn: incompat3){
				disconnectNodes(n, nn);
			}
		}
	}
	
	public List<Node> getListOfNodesFromListOfNodes(NodeList...nodelists){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (NodeList nl: nodelists){
			for (Node node: nl){
				if (!nodes.contains(node)){
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	public void connectNodes(List<Node>...nodeLists) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (List<Node> nodelist: nodeLists){
			nodes.addAll(nodelist);
		}
		for (Node n: nodes){
			for (Node nn: nodes){
				if (n != nn){
					connectNodes(n,nn);
				}
			}
		}
	}

	/**
	 * @return the List of Cliques Found
	 */
	public Set<Clique> getCliquesFound() {
	    ArrayList<Clique> l = new ArrayList<>();
	    l.addAll(cliquesFound);
	    Collections.sort(l);
		return cliquesFound;
	}

	public int getEdgeCount(){
	    return this.edgeCount;
	}
	
}

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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * A vertex in a graph. This is an "intelligent" entity in the graph and knows each of the
 * neighbors it has and can "communicate" with each of them.
 * Based on this communication it can compile the necessary information and calculate the cliques it belongs to.
 * 
 * @author Michael LaPlante
 *
 */
public class Node implements Comparable<Node>{
	public static long neighborIntroCounter;
	
	private String name;
	private Long id;
	
	private SortedSet<Node> neighbors = new TreeSet<Node>();
	private Set<Node> neighborsNeighbors = new HashSet<Node>();
	private SortedMap<Node,SortedSet<Node>> mutualNeighborsByNode = new TreeMap<>();
	private Table<Node,Node,Clique> _3cliqueMap = HashBasedTable.create();
	private List<Clique> _3cliques = new ArrayList<>();
	private Set<Clique> cliques = new HashSet<>();
	
	private int hashcode;
	private Graph nodeManager;
	
	public static boolean cliqueCalculationVerbose = false;
	public static boolean neighborIntroVerbose = false;
	
	public Node(String name, long id){
		this.name = name;
		this.id = id;
		this.hashcode = Long.hashCode(id);
	}
	
	   
    /**
     * Phase 1 of the algorithm. Tells each neighbor about this node's neighbors.
     */
    public void introduceNeighbors(){
        for (Node n: neighbors){
            int sharedCliqueOf3count =0;
            for (Node n1: neighbors){
                if (!n.equals(n1)){
                    if (n1.neighborIntroduction(this, n)){
                        sharedCliqueOf3count ++;
                    }
                    neighborIntroCounter++;
                }
            }
            if (sharedCliqueOf3count == 0){
                if (this.nodeManager != null){
                	Clique new2clique = new Clique(this,n);
                    this.nodeManager.reportCliqueFound(new2clique);
                    this.cliques.add(new2clique);
                }
            }
        }
    }

    /**
     * Builds list of 3 cliques per neighbor based on shared neighbors. Each neighbor of this node
     * calls this method to tell it about the neighbors.
     * @param source
     * @param n
     */
    public boolean neighborIntroduction(Node source, Node n){
        if (neighborIntroVerbose){
            System.out.printf("(%s) Neighbor %s introduced %s to me\n",this,source,n); 
        }
        neighborsNeighbors.add(n);
        if (hasNeighbor(n)){
            this.mutualNeighborsByNode.get(source).add(n);
            boolean hasCliqueWithBothNodes = _3cliqueMap.contains(source, n) || _3cliqueMap.contains(n, source);
            if (!hasCliqueWithBothNodes){
                Clique new3clique = new Clique(this,source,n);
                this._3cliques.add(new3clique);
                this._3cliqueMap.put(source, n, new3clique);
                if (neighborIntroVerbose){
                    System.out.printf("(%s) New Clique %s created\n", this, new3clique); 
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Phase 2 - after determining all of the 3-cliques that this node is a part of,
     * the larger cliques can be determined by merging all of the 3-cliques together.
     */
    public void calculateCliques(){
        if (cliqueCalculationVerbose){
            System.out.printf("I am %s\n", this);
            this.printOutNeighborhoodData();
        }
        // Build a lookup table of node pairs from the _3cliques, excluding this node 
        Table<Node, Node, Node[]> lookupTable = HashBasedTable.create();
        ArrayList<Node[]> pairList = new ArrayList<Node[]>();
        for (Clique c: this._3cliques){
            Node[] nodes = nodesToCompare(c);
            lookupTable.put(nodes[0], nodes[1],nodes);
            lookupTable.put(nodes[1], nodes[0], nodes);
            pairList.add(nodes);
        }
        if (cliqueCalculationVerbose){
            System.out.printf(" Pair List:\n");
            for (Node[] pair: pairList){
                System.out.printf("  %s,%s\n",pair[0],pair[1]);
            }
        }
        Set<Node> keyNodesSearched = new HashSet<Node>();
        Table<Node,Node,Boolean> mergedPairs = HashBasedTable.create();
        for (int i = 0; i<= pairList.size()-1; i++){
            Node[] nodes = pairList.get(i);
            if (mergedPairs.contains(nodes[0],nodes[1])){
                if (cliqueCalculationVerbose){
                    System.out.printf("      Skipping, because we've already merged this\n");
                }
                continue; // skip node pairs we've alread merged.
            }
            Set<Node> newCliqueList = new HashSet<Node>();
            // Select the key node to merge on
            Node keyNode = nodes[0];
            if (cliqueCalculationVerbose){
                System.out.printf("  Pair to analyze: %s,%s\n",nodes[0],nodes[1]);
                System.out.printf("  Key Node: %s\n",keyNode);
            }
            newCliqueList.add(nodes[0]);
            newCliqueList.add(nodes[1]);
            for (int ii = 0; ii <= pairList.size()-1; ii++){
                if (i == ii){
                    continue; // don't compare the node pair to itself
                }
                Node[] pairToCompare = pairList.get(ii);// rhyming is cool
                if (cliqueCalculationVerbose){
                    System.out.printf("   Pair to compare %s,%s:\n",pairToCompare[0],pairToCompare[1]);
                }
                Node searchNode = null;
                if (pairToCompare[0] == keyNode || pairToCompare[1] == keyNode){ // We've found a pair with our key node!
                    searchNode = pairToCompare[0] == keyNode ? pairToCompare[1] : pairToCompare[0]; // other node to search/merge on
                }
                if (searchNode != null){
                    if (cliqueCalculationVerbose){
                        System.out.printf("      Key node found. Searching for pairs containing %s\n",searchNode);
                    }
                    boolean hasAll = true;
                    for (Node n: newCliqueList){
                        if (!lookupTable.contains(n, searchNode)){   
                            if (cliqueCalculationVerbose){
                                System.out.printf("      No node pair %s,%s\n",n,searchNode);
                            }
                            hasAll = false;
                            break;
                        }
                    }
                    if (hasAll){
                        if (cliqueCalculationVerbose){
                            System.out.printf("      Node pairs found!\n",searchNode);
                        }
                        newCliqueList.add(searchNode);
                        // maintain the list of merged node pairs, so that they are not checked in the outer loop a second time, yielding a result already found 
                        for (Node n: newCliqueList){
                            mergedPairs.put(searchNode,n,true);
                            mergedPairs.put(n,searchNode,true);
                        }
                    }
                }
            }
            Clique largerClique = new Clique.CliqueBuilder().addNodes(this).addNodes(newCliqueList).build();
            if (largerClique != null){
                this.cliques.add(largerClique);
                if (this.nodeManager != null){
                    boolean shouldBreak = this.nodeManager.reportCliqueFound(largerClique);
                    if (shouldBreak){
                        break;
                    }
                }
            }
            if (newCliqueList.size() == this.neighborCount()){  // This means each of our 3-cliques all connect to each other,
                // hence, this node is a part of only 1 complete graph and
                // there is no need to continue checking all pairs
                if (Node.cliqueCalculationVerbose){
                    System.out.println("\tAll 3-cliques are a part of each other");
                }
                break;
            }
        }
    }
	
	public long getId(){
		return id;
	}
	
	public void setNeighbor(Node n){
		if (n.equals(this)){
			throw new RuntimeException(String.format("Cannot connect node %s to self", n));
		}
		this.neighbors.add(n);
		this.mutualNeighborsByNode.put(n, new TreeSet<>());
	}
	
	public void unsetNeighbor(Node n){
		this.neighbors.remove(n);
		this.mutualNeighborsByNode.remove(n, new TreeSet<>());
	}
	
	private Node[] nodesToCompare(Clique clique) {
		Node[] nodes = new Node[2];
		int i = 0;
		for (Node n : clique.getNodes()) {
			if (n != this) {
				nodes[i] = n;
				i++;
			}
		}
		return nodes;
	}
	
	public List<Clique> getDistinctCliquesBySize(){
		List<Clique> l = new ArrayList<Clique>();
		l.addAll(cliques);
		Collections.sort(l);
		return l;
	}
	
	public List<Clique> getCliquesSharedWithNode(Node node) {
		ArrayList<Clique> cliques = new ArrayList<>();
		for (Clique c: this.cliques){
			if (c.hasNodes(this,node)){
				cliques.add(c);
			}
		}
		return cliques;
	}

	public boolean hasNeighbor(Node n){
		return this.neighbors.contains(n);
	}
	
	public boolean hasNeighbors(Node...nodes){
		boolean hasNeighbors = true;
		for (Node n: nodes){
			if (!hasNeighbor(n)){
				hasNeighbors = false;
				break;
			}
		}
		return hasNeighbors;
	}
	
	public void printOutNeighborhoodData(){
		int neicount = this.mutualNeighborsByNode.keySet().size()+1;
		
		System.out.printf(" %s (%d)",this,this._3cliques.size());
		int cliqueCountPerRow = neicount == 0 ? 0 : this._3cliques.size()/neicount;
		int extra = cliqueCountPerRow == 0 ? 1 : this._3cliques.size() % cliqueCountPerRow;
		Iterator<Entry<Node, SortedSet<Node>>> it = this.mutualNeighborsByNode.entrySet().iterator();
		for (int i = 1; i <= neicount;i++){
			int s = i == 1 ? 0 : (i-1)*cliqueCountPerRow + extra;
			int e = s + cliqueCountPerRow;
			String cliques = "";
			for (Clique c: this._3cliques.subList(s, e)){
				cliques = cliques + convertCliqueToString(c);
			}
			if (it.hasNext()){
				Entry<Node, SortedSet<Node>> ent = it.next();
				Node nei = ent.getKey();
				SortedSet<Node> mutneis = ent.getValue();
				System.out.printf("\t%s\t%s\t%s\n",cliques,nei,mutneis);
			}
		}
		System.out.println();
	}
	
	private String convertCliqueToString(Clique c){
		StringBuilder sb = new StringBuilder("[");
		int i = 0;
		for (Node n: c.getNodes()){
			i++;
			sb.append(n.toString());
			if (i < c.getNodes().size()){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public int neighborCount(){
		return this.neighbors.size();
	}
	
	public Set<Node> neighbors(){
		return this.neighbors;
	}

	@Override
	public int compareTo(Node o) {
		return Long.compare(this.id, o.id);
	}
	
	public String getName() {
		return name;
	}

	public SortedMap<Node, SortedSet<Node>> getPotentialCliqueData() {
		return mutualNeighborsByNode;
	}

	public void setNodeManager(Graph nodeManager) {
		this.nodeManager = nodeManager;
	}

	/**
	 * Checks if the passed neighbor has the specified mutual neighbor
	 * @param neighbor This node's neighbor
	 * @param hasNeighbor The neighbor we are wondering if neighbor has
	 * @return <code>true</code> if neighbor knows hasNeighbor <code>false</code> otherwise
	 */
	public boolean neighborHas(Node neighbor, Node hasNeighbor) {
		return this.mutualNeighborsByNode.get(neighbor).contains(hasNeighbor);
	}
	
	public boolean isInCliqueWith(Node...nodes){
		if (nodes.length == 2){ // 3 clique including self
			for (Clique c: this._3cliques){
				if (c.hasNodes(nodes)){
					return true;
				}
			} 
		}else{
			for (Clique c: this.cliques){
				if (c.size() >= nodes.length){ // no use in testing a smaller clique than the number of nodes passed 
					if (c.hasNodes(nodes)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public Collection<? extends Node> getMutualNeighbors(Node neighbor) {
		return this.mutualNeighborsByNode.get(neighbor);
	}


    @Override
    public int hashCode(){
        return this.hashcode;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Node){
            return ((Node) o).getId() == this.getId();
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return name;
    }


    public int isolate() {
        int edgesRemoved = 0;
        Iterator<Node> it = this.neighbors.iterator();
        while (it.hasNext()){
            Node n = it.next();
            n.unsetNeighbor(this);
            it.remove();
            edgesRemoved++;
        }
        return edgesRemoved;
    }
    
	
}

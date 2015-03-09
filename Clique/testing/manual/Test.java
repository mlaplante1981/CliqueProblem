package manual;

import laplante.michael.clique.Clique;
import laplante.michael.clique.Graph;
import laplante.michael.clique.Node;

public class Test {

    public static void main(String[] args) {
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
        Node l = gr.newNode();
        Node m = gr.newNode();
        
        gr.connectNodes(a,b,c,d);
        gr.connectNodes(e,f,g,h);
        gr.connectNodes(d,h);
        
        gr.connectNodes(h,i,j);
        gr.connectNodes(k,l,m);
        
        gr.doCalculation();
        gr.printOut();
        
        for (Clique cl: gr.getCliquesFound()){
            System.out.println(cl);
        }

    }

}

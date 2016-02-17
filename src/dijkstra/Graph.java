/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Bancho
 */
public class Graph {
    
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Iterable<Node> getNodes(){
        return Collections.unmodifiableList(nodes);
    }

    public Node createNode(String name){
        Node node = new Node(name);
        nodes.add(node);
        return node;
    }

    public Edge createEdge(Node begin, Node end, double weight){
        Edge edge = new Edge(begin, end, weight);
        edges.add(edge);
        return edge;
    }

}

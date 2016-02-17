/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Bancho
 */
public class DijkstrasAlg {
    
    public static void main(String[] args) {
        Graph graph = new Graph();
        
        Node a = graph.createNode("A");
        Node b = graph.createNode("B");
        Node c = graph.createNode("C");
        Node d = graph.createNode("D");
        Node e = graph.createNode("E");
        Node f = graph.createNode("F");
        Node g = graph.createNode("G");
        //Node h = graph.createNode("H");
        
//        graph.createEdge(a, b, 3);
//        graph.createEdge(a, c, 5);
//        graph.createEdge(a, d, 4);
//        graph.createEdge(b, e, 10);
//        graph.createEdge(b, c, 6);
//        graph.createEdge(c, e, 8);
//        graph.createEdge(c, d, 12);
//        graph.createEdge(d, c, 10);
//        graph.createEdge(d, a, 5);
//        graph.createEdge(d, e, 11);
//        graph.createEdge(d, f, 8);
//        graph.createEdge(e, g, 5);
//        graph.createEdge(e, b, 7);
//        graph.createEdge(f, e, 7);
//        graph.createEdge(f, g, 25);
//        graph.createEdge(g, h, 10);
        
        graph.createEdge(a, b, 4);
        graph.createEdge(a, c, 3);
        graph.createEdge(a, e, 7);
        graph.createEdge(b, c, 6);
        graph.createEdge(b, d, 5);
        graph.createEdge(c, d, 11);
        graph.createEdge(c, e, 8);
        graph.createEdge(d, e, 2);
        graph.createEdge(d, g, 10);
        graph.createEdge(d, f, 2);
        graph.createEdge(e, g, 5);
        graph.createEdge(g, f, 3);
        
        DijkstrasAlg algo = new DijkstrasAlg();
        //Iterable<Node> path = algo.findShortestPath(graph, a, h);
        Iterable<Node> path = algo.findShortestPath(graph, a, f);
        for(Node node : path){
            System.out.println(node);
        }
    }
    
    public Iterable<Node> findShortestPath(Graph graph, Node start, Node goal){
        start.setCost(0);
        Queue<Node> unvisited = new PriorityQueue<Node>();
        for(Node node : graph.getNodes()){
            unvisited.add(node);
        }
        Node currentNode = start;
        while(true){
            for(Edge edge : currentNode){
                Node endOfEdge = edge.getEnd();
                if (unvisited.contains(endOfEdge)) {
                    double newCost = currentNode.getCost() + edge.getWeight();
                    if (newCost < endOfEdge.getCost()) {
                        endOfEdge.setCost(newCost);
                        endOfEdge.setPrev(currentNode);
                    }
                }
            }
            unvisited.remove(currentNode);
            if (currentNode == goal) {
                ArrayList<Node> shortestPath = new ArrayList<>();
                do {                    
                    shortestPath.add(currentNode);
                    currentNode = currentNode.getPrev();
                } while (currentNode != null);
                Collections.reverse(shortestPath);
                return shortestPath;
            }
            if (unvisited.isEmpty()) {
                return null;
            }
            currentNode = unvisited.poll();
            if (currentNode.getCost() == Double.POSITIVE_INFINITY) {
                return null;
            }
        }
    }
}

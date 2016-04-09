/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Tobias Grundtvig
 */
public class Runner
{
    public static void main(String[] args)
    {
        Heap<Node> heap = new Heap<Node>(Comparator.naturalOrder());
        
        Node a = new Node("A", 3);
        Node b = new Node("B", 2);
        Node c = new Node("C", 5);
        Node d = new Node("D", 4);
        Node e = new Node("E", 7);
        Node f = new Node("F", 8);
        Node g = new Node("G", 13);
        Node h = new Node("H", 17);
        Node i = new Node("I", 6);
        Node j = new Node("J", 23);
        Node k = new Node("K", 8);
        Node l = new Node("L", 7);
        Node m = new Node("M", 3);
        
        heap.add(a);
        heap.add(b);
        heap.add(c);
        heap.add(d);
        heap.add(e);
        heap.add(f);
        heap.add(g);
        heap.add(h);
        heap.add(i);
        heap.add(j);
        heap.add(k);
        heap.add(l);
        heap.add(m);
        
        h.setValue(0);
        heap.update(h);
        b.setValue(20);
        heap.update(b);
        
        while(!heap.isEmpty())
        {
            Node n = heap.poll();
            System.out.println(n);
        }
        System.out.println("\nDone!");
    }
}

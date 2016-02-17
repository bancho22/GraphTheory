/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

import static a_star.Colors.ANSI_GREEN;
import static a_star.Colors.ANSI_RED;
import static a_star.Colors.ANSI_RESET;
import static a_star.Colors.ANSI_YELLOW;
import java.util.HashSet;


/**
 *
 * @author Bancho
 */
public class Runner {
    
    public static void main(String[] args) {
//        Grid grid = new Grid(5, 5);
//        Tile start = grid.getTileByCoords(1, 2);
//        Tile goal = grid.getTileByCoords(3, 4);
//        grid.getTileByCoords(2, 1).setBlocked(true);
//        grid.getTileByCoords(2, 2).setBlocked(true);
//        grid.getTileByCoords(2, 3).setBlocked(true);
//        grid.getTileByCoords(1, 3).setBlocked(true);
        
        Grid grid = new Grid(6, 7);
        Tile start = grid.getTileByCoords(1, 2);
        Tile goal = grid.getTileByCoords(4, 5);
        
        A_Star astarAlgo = new A_Star();
        Iterable<Tile> solution = astarAlgo.solve(grid, start, goal);
        for (Tile tile : solution) {
            System.out.println(Coords.stringify(tile.getCoords().getX(), tile.getCoords().getY()));
        }
        
        
        //---------------------printing start----------------------------------
        HashSet<Tile> path = new HashSet<Tile>();
        for (Tile tile : solution) {
            path.add(tile);
        }
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                    if (grid.getTileByCoords(x, y) == start) {
                        System.out.print(ANSI_YELLOW + "S " + ANSI_RESET);
                    }else if(grid.getTileByCoords(x, y) == goal){
                        System.out.print(ANSI_YELLOW + "G " + ANSI_RESET);
                    }else if(path.contains(grid.getTileByCoords(x, y))){
                        System.out.print(ANSI_GREEN + "P " + ANSI_RESET);
                    }else if(grid.getTileByCoords(x, y).isBlocked()){
                        System.out.print(ANSI_RED + "B " + ANSI_RESET);
                    }else{
                        System.out.print("X ");
                    }
            }
            System.out.println("");
        }
        //---------------------printing end----------------------------------
    }
}

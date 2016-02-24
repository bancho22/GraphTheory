/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

import static a_star.Colors.ANSI_PURPLE;
import static a_star.Colors.ANSI_RED;
import static a_star.Colors.ANSI_RESET;
import static a_star.Colors.ANSI_YELLOW;
import heap.Heap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Bancho
 */
public class A_Star {
    
    private final int COST_PER_ONE_AXIS_MOVEMENT = 10;
    private final int COST_PER_DIAGONAL_MOVEMENT = 14;
    
    public Iterable<Tile> solve(Grid grid, Tile start, Tile goal){
        calculateHeuristics(grid, goal);
        
        //PriorityQueue<Tile> openList = new PriorityQueue<Tile>();
        Heap<Tile> openList = new Heap<Tile>(Comparator.naturalOrder());
        HashSet<Tile> closedList = new HashSet<Tile>();
        
        boolean reachedGoal = false;
        Tile currentTile = start;
        do {
            for (Tile testedTile : currentTile.getNeighbouringTiles()) {
                if (testedTile.isBlocked() == false && closedList.contains(testedTile) == false) {
                    boolean isTestedTileDiagonal = false;
                    if (grid.isDiagonalMovementEnabled()) {
                        isTestedTileDiagonal = testedTile.getCoords().getX() != currentTile.getCoords().getX() &&
                                    testedTile.getCoords().getY() != currentTile.getCoords().getY();
                    }
                    if (openList.contains(testedTile)) {
                        int newGValue;
                        if (isTestedTileDiagonal) {
                            newGValue = currentTile.getGValue() + COST_PER_DIAGONAL_MOVEMENT;
                        }
                        else{
                            newGValue = currentTile.getGValue() + COST_PER_ONE_AXIS_MOVEMENT;
                        }
                        if (newGValue < testedTile.getGValue()) {
                            testedTile.setGValue(newGValue);
                            testedTile.setParentTile(currentTile);
                            openList.update(testedTile);
                            //openList.remove(testedTile);
                            //openList.add(testedTile);
                        }
                    }else{
                        if (isTestedTileDiagonal) {
                            testedTile.setGValue(currentTile.getGValue() + COST_PER_DIAGONAL_MOVEMENT);
                        }
                        else{
                            testedTile.setGValue(currentTile.getGValue() + COST_PER_ONE_AXIS_MOVEMENT);
                        }
                        testedTile.setParentTile(currentTile);
                        openList.add(testedTile);
                    }
                }
            }
            closedList.add(currentTile);
            currentTile = openList.poll();
            if (currentTile.equals(goal)) {
                reachedGoal = true;
            }
        } while (reachedGoal == false);

        //---------------------printing start----------------------------------
//        for (int y = 0; y < grid.getHeight(); y++) {
//            for (int x = 0; x < grid.getWidth(); x++) {
//                    if (grid.getTileByCoords(x, y) == start) {
//                        System.out.print(ANSI_YELLOW + "S " + ANSI_RESET);
//                    }else if(grid.getTileByCoords(x, y) == goal){
//                        System.out.print(ANSI_YELLOW + "G " + ANSI_RESET);
//                    }else if(closedList.contains(grid.getTileByCoords(x, y))){
//                        System.out.print(ANSI_PURPLE + "T " + ANSI_RESET);
//                    }else if(grid.getTileByCoords(x, y).isBlocked()){
//                        System.out.print(ANSI_RED + "B " + ANSI_RESET);
//                    }else{
//                        System.out.print("X ");
//                    }
//            }
//            System.out.println("");
//        }
        //---------------------printing end----------------------------------
        
        ArrayList<Tile> shortestPath = new ArrayList<Tile>();
        currentTile = goal;
        while(currentTile != start){
            shortestPath.add(currentTile);
            currentTile = currentTile.getParentTile();
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }
    
    private void calculateHeuristics(Grid grid, Tile goal){
        int goal_x = goal.getCoords().getX();
        int goal_y = goal.getCoords().getY();
        int curr_x;
        int curr_y;
        for (Tile tile : grid.getAllTiles()) {
            if (tile.isBlocked() == false) {
                curr_x = tile.getCoords().getX();
                curr_y = tile.getCoords().getY();
                tile.setHValue((Math.abs(goal_x - curr_x) + Math.abs(goal_y - curr_y)) * COST_PER_ONE_AXIS_MOVEMENT);
            }
        }
    }
}

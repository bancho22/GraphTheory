/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_visualisation;

import a_star.Grid;
import a_star.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Bancho
 * It's modified as in it contains additional class attributes which are used only for the visualization.
 * Another difference is that the algorithm stops if it realizes that the tile it's looking at (testedTile) is the goal tile,
 * instead of waiting for it to become the current one (currentTile).
 */
public class Modified_A_Star {
    
    private final int COST_PER_MOVEMENT = 10;
    
    //visualisation attributes
    private final ArrayList<Tile> testedTiles;
    private double timeElapsedSinceAnimationStart;
    private int revealedTestedTilesCounter;
    private boolean revealShortestPath;
    private boolean visualisationStarted;

    public Modified_A_Star() {
        this.testedTiles = new ArrayList<>();
        this.timeElapsedSinceAnimationStart = 0.0;
        this.revealedTestedTilesCounter = 0;
        this.revealShortestPath = false;
    }
    
    
    public Iterable<Tile> solve(Grid grid, Tile start, Tile goal){
        calculateHeuristics(grid, goal);
        
        PriorityQueue<Tile> openList = new PriorityQueue<Tile>();
        HashSet<Tile> closedList = new HashSet<Tile>();
        
        boolean reachedGoal = false;
        Tile currentTile = start;
        do {
            for (Tile testedTile : currentTile.getNeighbouringTiles()) {
                if (testedTile.isBlocked() == false && closedList.contains(testedTile) == false) {
                    if (testedTile.equals(goal)) {
                        testedTile.setParentTile(currentTile);
                        reachedGoal = true;
                        break;
                    }
                    if (openList.contains(testedTile)) {
                        int newGValue = currentTile.getGValue() + COST_PER_MOVEMENT;
                        if (newGValue < testedTile.getGValue()) {
                            testedTile.setGValue(newGValue);
                            testedTile.setParentTile(currentTile);
                            openList.remove(testedTile);
                            openList.add(testedTile);
                        }
                    }else{
                        testedTile.setGValue(currentTile.getGValue() + COST_PER_MOVEMENT);
                        testedTile.setParentTile(currentTile);
                        openList.add(testedTile);
                    }
                }
            }
            closedList.add(currentTile);
            currentTile = openList.poll();
            //the following if-statement  is not part of the algorithm, but is needed for the visualisation to work
            if (currentTile.equals(goal) == false) {
                testedTiles.add(currentTile);
            }
            //end of the if-statement
        } while (reachedGoal == false);
        
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
                tile.setHValue((Math.abs(goal_x - curr_x) + Math.abs(goal_y - curr_y)) * 10);
            }
        }
    }
    
    public void update(double time){
        if (revealedTestedTilesCounter < testedTiles.size()) {
            if (time > timeElapsedSinceAnimationStart + 0.3 && visualisationStarted) {
                revealedTestedTilesCounter++;
                timeElapsedSinceAnimationStart = time;
            }
        }else{
            revealShortestPath = true;
        }
    }
    
    public List<Tile> getRevealedTestedTiles(){
        return testedTiles.subList(0, revealedTestedTilesCounter);
    }
    
    public boolean revealShortestPath(){
        return revealShortestPath;
    }

    public void startVisualisation() {
        visualisationStarted = true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 *
 * @author Bancho
 */
public class Tile implements Observer, Comparable<Tile> {

    private final Grid grid; //owning grid
    private final Coords coords;
    private int hValue; //heuristic value
    private int gValue; //movement cost from the start tile to this one
    private boolean isBlocked;
    private Tile parentTile;
    private HashMap<Tile, String> neighbouringTilesRelativeLocations; //0 - north, 1 - east, 2 - south, 3 - west, 01 - northeast, 03 - northwest, 21 - southeast, 23 - southwest
    private int directionOfMovingObject; //must be set only for the start tile before the algorithm is started; 0 - north, 1 - east, 2 - south, 3 - west

    protected Tile(Grid grid, Coords coords) {
        this.grid = grid;
        this.coords = coords;
        hValue = 0;
        gValue = 0;
        isBlocked = false;
        parentTile = null;
        neighbouringTilesRelativeLocations = new HashMap<>();

        grid.addObserver(this);
    }

    public int getHValue() {
        return hValue;
    }

    public void setHValue(int hValue) {
        this.hValue = hValue;
    }

    public int getGValue() {
        return gValue;
    }

    public void setGValue(int gValue) {
        this.gValue = gValue;
    }

    public int getFValue() {
        if (isBlocked) {
            return Integer.MAX_VALUE;
        }
        else{
            return gValue + hValue;
        }
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Coords getCoords() {
        return coords;
    }

    public Tile getParentTile() {
        return parentTile;
    }

    public void setParentTile(Tile parent) {
        this.parentTile = parent;
    }

    public Set<Tile> getNeighbouringTiles(){
        return neighbouringTilesRelativeLocations.keySet();
    }

    public int getDirection() {
        return directionOfMovingObject;
    }

    public void setDirection(int directionOfMovingObject) {
        this.directionOfMovingObject = directionOfMovingObject;
    }
    

    @Override
    public void update(Observable o, Object arg) {
        locateNeighbouringTiles();
    }
    
    private void locateNeighbouringTiles(){
        Tile north = grid.getTileByCoords(coords.getX(), coords.getY() - 1);
        if (north != null) {
            neighbouringTilesRelativeLocations.put(north, "0");
        }
        Tile south = grid.getTileByCoords(coords.getX(), coords.getY() + 1);
        if (south != null) {
            neighbouringTilesRelativeLocations.put(south, "2");
        }
        Tile east = grid.getTileByCoords(coords.getX() + 1, coords.getY());
        if (east != null) {
            neighbouringTilesRelativeLocations.put(east, "1");
        }
        Tile west = grid.getTileByCoords(coords.getX() - 1, coords.getY());
        if (west != null) {
            neighbouringTilesRelativeLocations.put(west, "3");
        }

        if (grid.isDiagonalMovementEnabled()) {
            Tile northeast = grid.getTileByCoords(coords.getX() + 1, coords.getY() - 1);
            if (northeast != null) {
                neighbouringTilesRelativeLocations.put(northeast, "01");
            }
            Tile southeast = grid.getTileByCoords(coords.getX() + 1, coords.getY() + 1);
            if (southeast != null) {
                neighbouringTilesRelativeLocations.put(southeast, "21");
            }
            Tile southwest = grid.getTileByCoords(coords.getX() - 1, coords.getY() + 1);
            if (southwest != null) {
                neighbouringTilesRelativeLocations.put(southwest, "23");
            }
            Tile northwest = grid.getTileByCoords(coords.getX() - 1, coords.getY() - 1);
            if (northwest != null) {
                neighbouringTilesRelativeLocations.put(northwest, "03");
            }
        }
    }
    
    
    protected String getMovementRequiredToNeighbour(Tile neighbour){
        if (grid.isDiagonalMovementEnabled() == false) {
            int neighbourRelativeLoc = Integer.parseInt(neighbouringTilesRelativeLocations.get(neighbour));
            switch(Math.abs(neighbourRelativeLoc - directionOfMovingObject)){
                case 0:
                    return "f"; //forward movement required
                case 1:
                    return "s"; //side movement required
                case 2:
                    return "b"; //backwards movement required
                case 3:
                    return "s"; //side movement required
            }
        }else{
            return "dir feature only available for non-diagonal movement grids";
        }
        return null;
    }
    
    protected void calcOwnDirBasedOnParent(){
        int parentRelativeLoc = Integer.parseInt(neighbouringTilesRelativeLocations.get(parentTile));
        if(parentTile.getMovementRequiredToNeighbour(this).equals("b") == false){ //means that moving object didn't reach this tile using backwards movement
            this.setDirection((parentRelativeLoc + 2) % 4);
        }else{
            this.setDirection(parentRelativeLoc);
        }
    }
    

    @Override
    public int compareTo(Tile o) {
        if (this.getFValue() > o.getFValue()) {
            return 1;
        }
        if(this.getFValue() < o.getFValue()){
            return -1;
        }
        if (this.getHValue() > o.getHValue()) {
            return 1;
        }
        if(this.getHValue() < o.getHValue()){
            return -1;
        }
        return 0;
    }

}

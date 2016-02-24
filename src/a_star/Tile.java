/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
    private ArrayList<Tile> neighbouringTiles;

    protected Tile(Grid grid, Coords coords) {
        this.grid = grid;
        this.coords = coords;
        hValue = 0;
        gValue = 0;
        isBlocked = false;
        parentTile = null;
        neighbouringTiles = new ArrayList<>();

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

    public ArrayList<Tile> getNeighbouringTiles(){
        return neighbouringTiles;
    }
    

    @Override
    public void update(Observable o, Object arg) {
        locateNeighbouringTiles();
    }
    
    private void locateNeighbouringTiles(){
        Tile north = grid.getTileByCoords(coords.getX(), coords.getY() - 1);
        if (north != null) {
            neighbouringTiles.add(north);
        }
        Tile south = grid.getTileByCoords(coords.getX(), coords.getY() + 1);
        if (south != null) {
            neighbouringTiles.add(south);
        }
        Tile east = grid.getTileByCoords(coords.getX() + 1, coords.getY());
        if (east != null) {
            neighbouringTiles.add(east);
        }
        Tile west = grid.getTileByCoords(coords.getX() - 1, coords.getY());
        if (west != null) {
            neighbouringTiles.add(west);
        }

        if (grid.isDiagonalMovementEnabled()) {
            Tile northeast = grid.getTileByCoords(coords.getX() + 1, coords.getY() - 1);
            if (northeast != null) {
                neighbouringTiles.add(northeast);
            }
            Tile southeast = grid.getTileByCoords(coords.getX() + 1, coords.getY() + 1);
            if (southeast != null) {
                neighbouringTiles.add(southeast);
            }
            Tile southwest = grid.getTileByCoords(coords.getX() - 1, coords.getY() + 1);
            if (southwest != null) {
                neighbouringTiles.add(southwest);
            }
            Tile northwest = grid.getTileByCoords(coords.getX() - 1, coords.getY() - 1);
            if (northwest != null) {
                neighbouringTiles.add(northwest);
            }
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

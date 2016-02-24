/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

import java.util.HashMap;
import java.util.Observable;



/**
 *
 * @author Bancho
 */
public class Grid extends Observable {
    
    private final int width; //x axis
    private final int height; //y axis
    private final boolean diagonalMovementEnabled;
    private final HashMap<String, Tile> tiles; //key has following format: "(x,y)"
    
    public Grid(int width, int height, boolean diagonalMovementEnabled){
        this.width = width;
        this.height = height;
        this.diagonalMovementEnabled = diagonalMovementEnabled;
        tiles = new HashMap<>();
        initTiles();
    }
    
    private void initTiles(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Coords c = new Coords(x, y);
                tiles.put(Coords.stringify(c.getX(), c.getY()), new Tile(this, c));
            }
        }
        setChanged();
        notifyObservers(); //each tile is notified that all the other tiles have been added to the grid, so they can start locating and storing their neighbours
    }
    
    //check for null when calling this method
    public Tile getTileByCoords(int x, int y){
        if (x >= width || y >= height) {
            return null;
        }
        String strCoords = Coords.stringify(x, y);
        return tiles.get(strCoords);
    }
    
    public Iterable<Tile> getAllTiles(){
        return tiles.values();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDiagonalMovementEnabled() {
        return diagonalMovementEnabled;
    }

}

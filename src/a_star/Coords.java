/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star;

/**
 *
 * @author Bancho
 */
public class Coords {
    
    private final int x;
    private final int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    //returns a stringified version of the two given coords in the follwing format: "(x,y)"
    public static String stringify(int x, int y){
        return "(" + x + "," + y + ")";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_visualisation;

import a_star.Grid;
import a_star.Tile;
import app2dapi.App2D;
import app2dapi.Device;
import app2dapi.Platform;
import app2dapi.geometry.G2D;
import app2dapi.graphics.Canvas;
import app2dapi.graphics.ColorFactory;
import app2dapi.input.keyboard.Key;
import app2dapi.input.keyboard.KeyPressedEvent;
import app2dapi.input.keyboard.KeyReleasedEvent;
import app2dapi.input.keyboard.KeyboardListener;
import app2dpcimpl.PCPlatformImpl;
import java.util.ArrayList;


/**
 *
 * @author Bancho
 */
public class A_Star_App implements App2D, KeyboardListener {
    
    private G2D g2d;
    private ColorFactory cf;
    private int screenHeight;
    private int screenWidth;
    private Grid grid;
    private int tileHeight;
    private int tileWidth;
    private Tile startTile;
    private Tile endTile;
    private Modified_A_Star a_star;
    private ArrayList<Tile> shortestPath;


    @Override
    public boolean showMouseCursor() {
        return false;
    }

    @Override
    public boolean initialize(Device device) {
        g2d = device.getGeometry2D();
        cf = device.getScreen().getColorFactory();
        device.getKeyboard().addKeyboardListener(this);
        screenHeight = device.getScreen().getPixelHeight();
        screenWidth = device.getScreen().getPixelWidth();
        
        grid = new Grid(10, 10, true);
        tileHeight = screenHeight / grid.getHeight();
        tileWidth = screenWidth / grid.getWidth();
        
        grid.getTileByCoords(8, 2).setBlocked(true);
        grid.getTileByCoords(7, 3).setBlocked(true);
        grid.getTileByCoords(6, 4).setBlocked(true);
        grid.getTileByCoords(5, 5).setBlocked(true);
        //grid.getTileByCoords(4, 6).setBlocked(true);
        grid.getTileByCoords(3, 7).setBlocked(true);
        grid.getTileByCoords(2, 8).setBlocked(true);
        grid.getTileByCoords(1, 9).setBlocked(true);
        
        startTile = grid.getTileByCoords(1, 2);
        endTile = grid.getTileByCoords(7, 8);
        
        a_star = new Modified_A_Star();
        shortestPath = (ArrayList<Tile>) a_star.solve(grid, startTile, endTile);
        
//        for (Tile tile : shortestPath) {
//            System.out.println(Coords.stringify(tile.getCoords().getX(), tile.getCoords().getY()));
//        }

        return true;
    }

    @Override
    public boolean update(double time) {
        a_star.update(time);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.clear(cf.getWhite());

        canvas.setColor(cf.getBlack());
        canvas.drawFilledRectangle(g2d.newPoint2D(-screenWidth/2, screenHeight/2), g2d.newPoint2D(screenWidth/2, -screenHeight/2)); //setting the background to black
        
        //drawing the tiles
        canvas.setColor(cf.getGreen());
        int xLocBoundA = -screenWidth/2;
        int yLocBoundA = screenHeight/2;
        int xLocBoundB = -screenWidth/2 + tileWidth;
        int yLocBoundB = screenHeight/2 - tileHeight;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Tile tile = grid.getTileByCoords(x, y);
                if (tile.isBlocked()) {
                    canvas.setColor(cf.getRed());
                }else if(tile.equals(startTile) || tile.equals(endTile)){
                    canvas.setColor(cf.getBlue());
                }else if(a_star.getRevealedTestedTiles().contains(tile) && a_star.revealShortestPath() == false){
                    canvas.setColor(cf.getYellow());
                }else if(shortestPath.contains(tile) && a_star.revealShortestPath()){
                    canvas.setColor(cf.getWhite());
                }else{
                    canvas.setColor(cf.getGreen());
                }
                canvas.drawFilledRectangle(g2d.newPoint2D(xLocBoundA + 5, yLocBoundA - 5), g2d.newPoint2D(xLocBoundB - 5, yLocBoundB + 5));

                xLocBoundA += tileWidth;
                xLocBoundB += tileWidth;
            }
            xLocBoundA = -screenWidth/2;
            xLocBoundB = -screenWidth/2 + tileWidth;
            yLocBoundA -= tileHeight;
            yLocBoundB -= tileHeight;
        }

    }

    @Override
    public void destroy() {
        
    }

    @Override
    public void onKeyPressed(KeyPressedEvent e) {
        if (e.getKey().equals(Key.VK_SPACE)) {
            a_star.startVisualisation();
        }
    }

    @Override
    public void onKeyReleased(KeyReleasedEvent e) {

    }
    
    public static void main(String[] args) {
        Platform p = new PCPlatformImpl();
        App2D app = new A_Star_App();
        p.runApplication(app);
    }
}

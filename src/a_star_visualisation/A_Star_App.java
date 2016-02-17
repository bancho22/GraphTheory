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
import java.util.HashSet;


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
    private HashSet<Tile> testedTiles;
    private double timeElapsed;
    private int counter;
    private Modified_A_Star a_star;
    private ArrayList<Tile> shortestPath;
    private boolean showShortestPath;


    @Override
    public boolean showMouseCursor() {
        return true;
    }

    @Override
    public boolean initialize(Device device) {
        g2d = device.getGeometry2D();
        cf = device.getScreen().getColorFactory();
        device.getKeyboard().addKeyboardListener(this);
        screenHeight = device.getScreen().getPixelHeight();
        screenWidth = device.getScreen().getPixelWidth();
        
        grid = new Grid(10, 10);
        tileHeight = screenHeight / grid.getHeight();
        tileWidth = screenWidth / grid.getWidth();
        
        grid.getTileByCoords(8, 2).setBlocked(true);
        grid.getTileByCoords(7, 3).setBlocked(true);
        grid.getTileByCoords(6, 4).setBlocked(true);
        grid.getTileByCoords(5, 5).setBlocked(true);
        grid.getTileByCoords(4, 6).setBlocked(true);
        grid.getTileByCoords(3, 7).setBlocked(true);
        grid.getTileByCoords(2, 8).setBlocked(true);
        
        startTile = grid.getTileByCoords(1, 2);
        endTile = grid.getTileByCoords(7, 8);
        testedTiles = new HashSet<Tile>();
        timeElapsed = 0.0;
        counter = 0;
        showShortestPath = false;
        
        a_star = new Modified_A_Star();
        a_star.calculateHeuristics(grid, endTile);
        shortestPath = (ArrayList<Tile>) a_star.solve(grid, startTile, endTile);
        
//        for (Tile tile : shortestPath) {
//            System.out.println(Coords.stringify(tile.getCoords().getX(), tile.getCoords().getY()));
//        }

        return true;
    }

    @Override
    public boolean update(double time) {
        if (counter < a_star.getTestedTiles().size()) { //algorithm should be done in 3 secs
            if (time > timeElapsed + 0.3 && time > 3) {
                testedTiles.add(a_star.getTestedTiles().get(counter++));
                timeElapsed = time;
            }
        }else{
            showShortestPath = true;
        }
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
                }else if(testedTiles.contains(tile) && showShortestPath == false){
                    canvas.setColor(cf.getYellow());
                }else if(shortestPath.contains(tile) && showShortestPath){
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

//        System.out.println(yLocBoundA+" "+yLocBoundB);
//        System.out.println(-screenHeight/2 + "");
//        canvas.drawPoint(g2d.newPoint2D(xLocBoundA + 5, yLocBoundA - 5), 2);
//        canvas.drawPoint(g2d.newPoint2D(xLocBoundB - 5, yLocBoundB + 5), 2);

//        String text = "opa";
//        double textHeight = 50;
//        double textWidth = canvas.getTextWidth(text, textHeight);
//        canvas.drawText(g2d.newPoint2D(0 - textWidth/2, 0 - textHeight/2), text, textHeight);
    }

    @Override
    public void destroy() {
        
    }

    @Override
    public void onKeyPressed(KeyPressedEvent e) {
        if (e.getKey().equals(Key.VK_SPACE)) {
            //sth
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

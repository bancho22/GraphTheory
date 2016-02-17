/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_visualisation;

import app2dapi.geometry.G2D;
import app2dapi.graphics.Canvas;
import app2dapi.graphics.Color;
import app2dapi.graphics.ColorFactory;
import app2dapi.input.charinput.CharInputEvent;
import app2dapi.input.keyboard.KeyPressedEvent;
import app2dapi.input.keyboard.KeyReleasedEvent;
import app2dapi.panandzoom2dapp.PanAndZoom2DApp;
import app2dapi.panandzoom2dapp.PanAndZoomInit;
import app2dapi.panandzoom2dapp.PanAndZoomToolKit;

/**
 *
 * @author Bancho
 */
public class A_Star_PanAndZoomApp implements PanAndZoom2DApp {
    
    private G2D g2d;
    private ColorFactory cf;

    @Override
    public PanAndZoomInit initialize(PanAndZoomToolKit tk, double aspectRatio) {
        this.g2d = tk.g2d();
        this.cf = tk.cf();
        
        return new PanAndZoomInit(g2d.origo(), g2d.newPoint2D(1000.0, 1000.0 / aspectRatio), g2d.origo(), null, null, aspectRatio, aspectRatio, aspectRatio);
    }

    @Override
    public boolean showMouseCursor() {
        return true;
    }

    @Override
    public void onMouseMoved(G2D.Point2D mouseHUDPos, G2D.Point2D mouseWorldPos) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMousePressed(G2D.Point2D mouseHUDPos, G2D.Point2D mouseWorldPos) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMouseReleased(G2D.Point2D mouseHUDPos, G2D.Point2D mouseWorldPos) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onKeyPressed(KeyPressedEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onKeyReleased(KeyReleasedEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCharInput(CharInputEvent event) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(double time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color getBackgroundColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawWorld(Canvas canvas) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawHUD(Canvas canvas) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

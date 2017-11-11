/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class ShadowDrawer {
    
    public void draw(GraphicObject target, Graphics2D g2D, double x, double y){
        RectangularShape shape = Views.get(target.getClass()).getShape(target);
        double w = target.getBound().width();
        double h = target.getBound().height();
        shape.setFrame(x-w/2, y-h/2, w, h);
        target.notifyListeners();
        g2D.setColor(Color.gray);
        g2D.setStroke(Drawer2D.normalStroke);
        g2D.draw(shape);
    }
    
}

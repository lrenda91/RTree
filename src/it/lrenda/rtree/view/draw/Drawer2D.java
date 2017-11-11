/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

/**
 *
 * @author Luigi
 */
public interface Drawer2D<T extends Drawable> {
    
    static final BasicStroke dashedStroke = new BasicStroke(1,0, 0,1,new float[]{3},0);
    static final BasicStroke normalStroke = new BasicStroke();
    
    void draw(T t, Graphics2D g2D);
    
    RectangularShape getShape(T t);
    
}

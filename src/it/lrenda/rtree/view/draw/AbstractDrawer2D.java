/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.awt.Graphics2D;

/**
 *
 * @author Luigi
 */
public abstract class AbstractDrawer2D<T extends Drawable> implements Drawer2D<T> {

    @Override
    public void draw(T t, Graphics2D g2D) {
        if (t == null)
            return;
        g2D.draw(getShape(t));
    }

}

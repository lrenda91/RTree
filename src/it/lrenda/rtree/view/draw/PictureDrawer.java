/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.geom.Picture;

/**
 *
 * @author luigi
 */
public class PictureDrawer extends AbstractDrawer2D<Picture> {

    @Override
    public RectangularShape getShape(Picture t) {
        BoundingBox go = t.getBound();
        return new Rectangle2D.Double(go.getMinX(),go.getMinY(),go.width(),go.height());
    }

    @Override
    public void draw(Picture t, Graphics2D g2D) {
        BoundingBox box = t.getBound();
        g2D.drawImage(t.getImage(), (int)box.getMinX(), (int)box.getMinY(), 
                (int)box.width(), (int)box.height(), null);
    }
    
    
}

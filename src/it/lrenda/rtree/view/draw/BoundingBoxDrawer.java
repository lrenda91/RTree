/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import it.lrenda.rtree.objects.MBR.BoundingBox;

/**
 *
 * @author Luigi
 */
public class BoundingBoxDrawer extends AbstractDrawer2D<BoundingBox> {

    @Override
    public RectangularShape getShape(BoundingBox bb) {
        return new Rectangle2D.Double(bb.getMinX(),bb.getMinY(),bb.width(),bb.height());
    }
    
}

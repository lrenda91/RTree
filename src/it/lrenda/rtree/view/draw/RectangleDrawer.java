package it.lrenda.rtree.view.draw;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import it.lrenda.rtree.objects.geom.Rectangle;


public class RectangleDrawer extends AbstractDrawer2D<Rectangle> {

    @Override
    public RectangularShape getShape(Rectangle go) {
        return new Rectangle2D.Double(go.getMinX(),go.getMinY(),go.getWidth(),go.getHeight());
    }

}

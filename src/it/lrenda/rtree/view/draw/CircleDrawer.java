package it.lrenda.rtree.view.draw;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.geom.Circle;

public class CircleDrawer extends AbstractDrawer2D<Circle> {

    @Override
    public RectangularShape getShape(Circle c) {
        BoundingBox mbr = c.getBound();
        return new Ellipse2D.Double(mbr.getMinX(), mbr.getMinY(), mbr.width(), mbr.height());
    }  

}
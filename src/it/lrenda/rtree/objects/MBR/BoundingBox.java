package it.lrenda.rtree.objects.MBR;

import it.lrenda.rtree.view.draw.Drawable;
import it.lrenda.rtree.objects.Point2D;

public interface BoundingBox extends Drawable {
    
    double getMinX();

    double getMinY();

    double getMaxX();

    double getMaxY();

    double width();

    double height();
    
    double margin();

    boolean intersects(BoundingBox MBR);

    double enlargementArea(BoundingBox MBRtoAdd);

    Point2D center();

    boolean contains(Point2D p);

    boolean contains(BoundingBox MBR);

    double area();

    BoundingBox union(BoundingBox other);
        
}

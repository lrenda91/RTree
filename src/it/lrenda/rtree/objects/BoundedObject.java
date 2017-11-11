package it.lrenda.rtree.objects;

import it.lrenda.rtree.view.draw.Drawable;
import it.lrenda.rtree.objects.MBR.BoundingBox;

/**
 *
 * @author Luigi
 */
public interface BoundedObject extends Drawable {
    
    BoundingBox getBound();
    
    void setBound(BoundingBox bound);
    
}

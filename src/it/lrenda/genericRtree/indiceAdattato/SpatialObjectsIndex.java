package it.lrenda.genericRtree.indiceAdattato;

import java.util.List;
import it.lrenda.genericRtree.MBR.BoundingBox;
import it.lrenda.genericRtree.MBR.Point2D;

public interface SpatialObjectsIndex<K extends BoundingBox , V> {
    
    V exactMatchQuery(K MBR);
    
    List<Entry> pointQuery(Point2D p);
    
    List<Entry> rangeQuery(K MBR);
    
    void add(K MBR, V value);
    
    void delete(K MBR);
    
    void clear();
    
}

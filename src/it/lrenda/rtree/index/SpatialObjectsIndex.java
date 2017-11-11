package it.lrenda.rtree.index;

import java.util.List;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.GraphicObject;

public interface SpatialObjectsIndex {
    
    GraphicObject exactMatchQuery(GraphicObject MBR);
    
    List<GraphicObject> pointQuery(double x,double y);
    
    List<GraphicObject> rangeQuery(BoundingBox box);
    
    void add(GraphicObject obj);
    
    void delete(GraphicObject obj);
    
    void clear();
    
}

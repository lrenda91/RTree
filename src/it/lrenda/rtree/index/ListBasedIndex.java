package it.lrenda.rtree.index;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.Point2D;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class ListBasedIndex implements SpatialObjectsIndex {

    private List<GraphicObject> objects = new LinkedList<>();
    
    @Override
    public GraphicObject exactMatchQuery(GraphicObject MBR) {
        for (GraphicObject bo : objects){
            if (bo.equals(MBR))
                return bo;
        }
        return null;
    }

    @Override
    public List<GraphicObject> pointQuery(double x, double y) {
        Point2D p = new Point2D(x, y);
        List<GraphicObject> result = new ArrayList<>();
        for (GraphicObject bo : objects){
            if (bo.getBound().contains(p))
                result.add(bo);
        }
        return result;
    }

    @Override
    public List<GraphicObject> rangeQuery(BoundingBox box) {
        List<GraphicObject> result = new ArrayList<>();
        for (GraphicObject bo : objects){
            if (bo.getBound().intersects(box))
                result.add(bo);
        }
        return result;
    }

    @Override
    public void add(GraphicObject obj) {
        objects.add(obj);
    }

    @Override
    public void delete(GraphicObject obj) {
        objects.remove(obj);
    }

    @Override
    public String toString() {
        return objects.toString();
    }

    @Override
    public void clear() {
        objects.clear();
    }
    
}

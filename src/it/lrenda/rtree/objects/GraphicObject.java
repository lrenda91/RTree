package it.lrenda.rtree.objects;

/**
 *
 * @author Luigi
 */
public interface GraphicObject extends BoundedObject,Cloneable {
    
    void addGraphicObjectListener(GraphicObjectListener l);

    void removeGraphicObjectListener(GraphicObjectListener l);
    
    void notifyListeners();
    
    Point2D getPosition();

    void zoom(double factor);
    
    void moveTo(Point2D newPoint);

    boolean contains(Point2D p);

    String getType();
    
    GraphicObject copy();
    
}

package it.lrenda.rtree.objects;

import java.util.HashSet;
import java.util.Set;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.MBR.BoundingBoxImpl;

/**
 *
 * @author Luigi
 */
public abstract class AbstractGraphicObject implements GraphicObject {
    
    private Set<GraphicObjectListener> observers = new HashSet<>();
    protected BoundingBox box;
    protected Point2D position;

    @Override
    public void addGraphicObjectListener(GraphicObjectListener l) {
        observers.add(l);
    }

    @Override
    public void removeGraphicObjectListener(GraphicObjectListener l) {
        observers.remove(l);
    }

    @Override
    public void moveTo(Point2D newPoint) {
        position = newPoint;
        updateBound();
    }

    @Override
    public void notifyListeners() {
        for (GraphicObjectListener l : observers)
            l.update(this);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public BoundingBox getBound() {
        return box;
    }

    @Override
    public void setBound(BoundingBox bound) {
        box = bound;
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }
    
    protected abstract void updateBound();
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof AbstractGraphicObject)) return false;
        AbstractGraphicObject other = (AbstractGraphicObject) o;
        return this.position.equals(other.position) && this.box.equals(other.box);
    }

    @Override
    protected AbstractGraphicObject clone() throws CloneNotSupportedException {
        AbstractGraphicObject res = (AbstractGraphicObject) super.clone();
        res.box = new BoundingBoxImpl(box.getMinX(),box.getMaxX(),box.getMinY(),box.getMaxY());
        res.observers = new HashSet<>(this.observers);
        res.position = new Point2D(position.getX(),position.getY());
        return res;
    }

    @Override
    public GraphicObject copy() {
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return getType();
    }
    
}

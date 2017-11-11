package it.lrenda.rtree.objects.geom;

import it.lrenda.rtree.objects.AbstractGraphicObject;
import it.lrenda.rtree.objects.MBR.BoundingBoxImpl;
import it.lrenda.rtree.objects.Point2D;

/**
 *
 * @author Luigi
 */
public final class Rectangle extends AbstractGraphicObject {

    private double minX, minY, width, height;
    
    public Rectangle(Point2D center, double width, double height) {
        this.minX = center.getX()-width/2;
        this.width = width;
        this.minY = center.getY()-height/2;
        this.height = height;
        this.position = center; //punto centrale
        box = new BoundingBoxImpl(minX, minX+width, minY, minY+height);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    protected void updateBound() {
        minX = position.getX()-width/2;
        minY = position.getY()-height/2;
        box = new BoundingBoxImpl(minX, minX+width, minY, minY+height);
    }

    @Override
    public void zoom(double factor) {
        width*=factor;
        height*=factor;
        updateBound();
    }

    @Override
    public boolean contains(Point2D p) {
        return p.getX() >= this.minX && p.getX() <= (this.minX+width)
                && p.getY() >= this.minY && p.getY() <= (this.minY+height);
    }

    @Override
    public Rectangle clone() throws CloneNotSupportedException {
        return (Rectangle) super.clone();
    }

    @Override
    public String toString() {
        return super.toString()+"[("+minX+","+minY+"),"+width+"x"+height+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Rectangle)) return false;
        Rectangle r = (Rectangle) o;
        return minX == r.minX && minY == r.minY && width == r.width && height == r.height;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.objects.geom;

import it.lrenda.rtree.objects.AbstractGraphicObject;
import it.lrenda.rtree.objects.MBR.BoundingBoxImpl;
import it.lrenda.rtree.objects.Point2D;

/**
 *
 * @author Luigi
 */
public final class Circle extends AbstractGraphicObject {

    private double radius;
    
    public Circle(Point2D center, double radius) {
        this.position = center;
        this.radius = radius;
        updateBound();
    }

    public double radius() {
        return radius;
    }

    @Override
    public void zoom(double factor) {
        radius *= factor;
        updateBound();
    }

    @Override
    public boolean contains(Point2D p) {
        return p.distance(position) <= radius;
    }

    @Override
    protected void updateBound() {
        double minX = position.getX() - radius;
        double minY = position.getY() - radius;
        double maxX = position.getX() + radius;
        double maxY = position.getY() + radius;
        this.box = new BoundingBoxImpl(minX,maxX,minY,maxY);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Circle)) return false;
        Circle c = (Circle) o;
        return this.radius == c.radius;
    }

    @Override
    public Circle clone() throws CloneNotSupportedException {
        return (Circle) super.clone();
    }

    @Override
    public String toString() {
        return super.toString()+"[("+position.getX()+","+position.getY()+"),r="+radius+"]";
    }
    
}

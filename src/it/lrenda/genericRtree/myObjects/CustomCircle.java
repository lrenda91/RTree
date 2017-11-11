/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.myObjects;

import it.lrenda.genericRtree.MBR.Point2D;


/**
 *
 * @author Luigi
 */
public final class CustomCircle implements CustomObject {

    private double radius;
    private Point2D center;
    
    public CustomCircle(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public double radius() {
        return radius;
    }

    public Point2D getCenter() {
        return center;
    }

    public boolean contains(Point2D p) {
        return p.distance(center) <= radius;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof CustomCircle)) return false;
        CustomCircle c = (CustomCircle) o;
        return this.radius == c.radius;
    }

    @Override
    public String toString() {
        return "Circle[("+center.getX()+","+center.getY()+"),r="+radius+"]";
    }
    
}

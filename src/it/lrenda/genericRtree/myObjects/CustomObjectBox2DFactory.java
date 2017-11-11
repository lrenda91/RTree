/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.myObjects;

import it.lrenda.genericRtree.MBR.BoundingBox2D;
import it.lrenda.genericRtree.MBR.Point2D;
import java.awt.Dimension;

/**
 *
 * @author luigi
 */
public class CustomObjectBox2DFactory {
    
    private CustomObjectBox2DFactory(){}

    public static BoundingBox2D getBox(CustomObject co){
        if (co instanceof CustomCircle) return getBox((CustomCircle) co);
        if (co instanceof CustomPicture) return getBox((CustomPicture) co);
        return getBox((CustomRectangle) co);
    }
    
    public static BoundingBox2D getBox(CustomCircle c){
        double r = c.radius();
        double x = c.getCenter().getX();
        double y = c.getCenter().getY();
        return new BoundingBox2D(x-r, x+r, y-r, y+r);
    }
    
    public static BoundingBox2D getBox(CustomRectangle r){
        return new BoundingBox2D(r.getMinX(), r.getMinX()+r.getWidth(), r.getMinY(), r.getMinY()+r.getHeight());
    }
    
    public static BoundingBox2D getBox(CustomPicture p){
        Point2D center = p.getCenter();
        Dimension dim = p.getDim();
        double minX = center.getX()-dim.width/2;
        double maxX = center.getX()+dim.width/2;
        double minY = center.getY()-dim.height/2;
        double maxY = center.getY()+dim.height/2;
        return new BoundingBox2D(minX, maxX, minY, maxY);
    }
    
}

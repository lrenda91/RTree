/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.myObjects;

import it.lrenda.genericRtree.MBR.Point2D;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 *
 * @author luigi
 */
public final class CustomPicture implements CustomObject {

    private Image image;
    private Dimension dim;
    private Point2D center;

    public CustomPicture(ImageIcon image, Point2D center, int width, int heigth) {
        this.image = (image == null ? null : image.getImage());
        this.center = center;
        this.dim = new Dimension(width,heigth);
    }

    public Image getImage() {
        return image;
    }

    public Dimension getDim() {
        return dim;
    }

    public Point2D getCenter() {
        return center;
    }
    
    public boolean contains(Point2D p) {
        double minX = center.getX()-dim.width/2;
        double maxX = center.getX()+dim.width/2;
        double minY = center.getY()-dim.height/2;
        double maxY = center.getY()+dim.height/2;
        return p.getX() >= minX && p.getX() <= maxX &&
                p.getY() >= minY && p.getY() <= maxY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof CustomPicture)) return false;
        CustomPicture p = (CustomPicture) o;
        return image.equals(p.image) && dim.equals(p.dim);
    }
    
}

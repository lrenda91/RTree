/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.objects.geom;

import it.lrenda.rtree.objects.AbstractGraphicObject;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import it.lrenda.rtree.objects.MBR.BoundingBoxImpl;
import it.lrenda.rtree.objects.Point2D;

/**
 *
 * @author luigi
 */
public final class Picture extends AbstractGraphicObject {

    private Image image;
    private Dimension dim;

    public Picture(ImageIcon image, Point2D center, int width, int heigth) {
        this.image = (image == null ? null : image.getImage());
        this.position = center;
        this.dim = new Dimension(width,heigth);
        updateBound();
    }

    public Image getImage() {
        return image;
    }

    public Dimension getDim() {
        return dim;
    }
    
    @Override
    protected void updateBound() {
        double minX = position.getX()-dim.width/2;
        double minY = position.getY()-dim.height/2;
        double maxX = position.getX()+dim.width/2;
        double maxY = position.getY()+dim.height/2;
        box = new BoundingBoxImpl(minX, maxX, minY, maxY);
    }

    @Override
    public void zoom(double factor) {
        dim.setSize(dim.width*factor, dim.height*factor);
        updateBound();
    }
    
    @Override
    public boolean contains(Point2D p) {
        return box.contains(p);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Picture)) return false;
        Picture p = (Picture) o;
        return image.equals(p.image) && dim.equals(p.dim);
    }
    
}

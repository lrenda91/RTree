package it.lrenda.genericRtree.myObjects;

import it.lrenda.genericRtree.MBR.Point2D;


/**
 *
 * @author Luigi
 */
public final class CustomRectangle implements CustomObject {

    private double minX, minY, width, height;
    
    public CustomRectangle(Point2D center, double width, double height) {
        this.minX = center.getX()-width/2;
        this.width = width;
        this.minY = center.getY()-height/2;
        this.height = height;
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

    public boolean contains(Point2D p) {
        return p.getX() >= this.minX && p.getX() <= (this.minX+width)
                && p.getY() >= this.minY && p.getY() <= (this.minY+height);
    }

    @Override
    public String toString() {
        return "Rectangle[("+minX+","+minY+"),"+width+"x"+height+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof CustomRectangle)) return false;
        CustomRectangle r = (CustomRectangle) o;
        return minX == r.minX && minY == r.minY && width == r.width && height == r.height;
    }
    
}

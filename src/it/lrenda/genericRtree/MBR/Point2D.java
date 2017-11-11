package it.lrenda.genericRtree.MBR;

/**
 *
 * @author Luigi
 */
public class Point2D extends AbstractPoint {

    public Point2D(double x, double y) {
        super(x,y);
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }
    
}

package it.lrenda.rtree.objects;

/**
 *
 * @author Luigi
 */
public class Point2D {

    private double x,y;

    public Point2D(double x, double y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("x<0 or y<0");
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Point2D p) {
        double cateto1 = Math.abs(this.x - p.getX());
        double cateto2 = Math.abs(this.y - p.getY());
        return Math.sqrt(Math.pow(cateto1,2) + Math.pow(cateto2,2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point2D)) return false;
        Point2D p = (Point2D) obj;
        return x == p.x && y == p.y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
    
}

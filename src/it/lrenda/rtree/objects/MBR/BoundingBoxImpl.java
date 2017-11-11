package it.lrenda.rtree.objects.MBR;

import it.lrenda.rtree.objects.Point2D;

/**
 *
 * @author Luigi
 */
public class BoundingBoxImpl implements BoundingBox {

    private double minX, maxX, minY, maxY;

    public BoundingBoxImpl(double minX, double maxX, double minY, double maxY) {
        if (minX > maxX || minY > maxY)
            throw new IllegalArgumentException("Wrong values");
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    
    @Override
    public double getMinX() {
        return minX;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxX() {
        return maxX;
    }

    @Override
    public double getMaxY() {
        return maxY;
    }
 
    @Override
    public double width() {
        return maxX - minX;
    }

    @Override
    public double height() {
        return maxY - minY;
    }

    @Override
    public double margin() {
        return width() + height();
    }

    @Override
    public double area() {
        return width() * height();
    }
    
    @Override
    public Point2D center() {
        double centerX = width() / 2;
        double centerY = height() / 2;
        return new Point2D(centerX,centerY);
    }
    
    @Override
    public boolean contains(Point2D p) {
        return maxX >= p.getX() && minX <= p.getX() &&
                maxY >= p.getY() && minY <= p.getY();
    }
    
    @Override
    public double enlargementArea(BoundingBox MBRtoAdd) {
        return union(MBRtoAdd).area() - this.area();
    }

    @Override
    public BoundingBox union(BoundingBox other) {
        double unionMinX = Math.min(this.minX,other.getMinX());
        double unionMaxX = Math.max(this.maxX,other.getMaxX());
        double unionMinY = Math.min(this.minY,other.getMinY());
        double unionMaxY = Math.max(this.maxY,other.getMaxY());
        return new BoundingBoxImpl(unionMinX, unionMaxX, unionMinY, unionMaxY);
    }
    
    @Override
    public boolean intersects(BoundingBox MBR) {
        return !( maxX <= MBR.getMinX() || minX >= MBR.getMaxX() ||
		maxY <= MBR.getMinY() || minY >= MBR.getMaxY() );
    }
    
    @Override
    public boolean contains(BoundingBox MBR) {
        return minX <= MBR.getMinX() && maxX >= MBR.getMaxX() &&
                minY <= MBR.getMinY() && maxY >= MBR.getMaxY();
    }

    
    
    @Override
    public String toString() {
        return "[("+(int)minX+","+(int)minY+"),("+(int)maxX+","+(int)maxY+")]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof BoundingBoxImpl)) return false;
        BoundingBoxImpl other = (BoundingBoxImpl) o;
        return minX == other.minX && maxX == other.maxX &&
                minY == other.minY && maxY == other.maxY;
    }
}

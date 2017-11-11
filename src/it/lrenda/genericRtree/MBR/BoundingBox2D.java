package it.lrenda.genericRtree.MBR;

/**
 *
 * @author Luigi
 */
public class BoundingBox2D implements BoundingBox<Point2D> {

    private double minX, maxX, minY, maxY;

    public BoundingBox2D(double minX, double maxX, double minY, double maxY) {
        if (minX > maxX || minY > maxY)
            throw new IllegalArgumentException("Wrong values");
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    
    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }
 
    public double width() {
        return maxX - minX;
    }

    public double height() {
        return maxY - minY;
    }

    public double margin() {
        return width() + height();
    }

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
    
    public double enlargementArea(BoundingBox2D MBRtoAdd) {
        return union(MBRtoAdd).area() - this.area();
    }

    public BoundingBox2D union(BoundingBox2D other) {
        double unionMinX = Math.min(this.minX,other.getMinX());
        double unionMaxX = Math.max(this.maxX,other.getMaxX());
        double unionMinY = Math.min(this.minY,other.getMinY());
        double unionMaxY = Math.max(this.maxY,other.getMaxY());
        return new BoundingBox2D(unionMinX, unionMaxX, unionMinY, unionMaxY);
    }
    
    public boolean intersects(BoundingBox2D MBR) {
        return !( maxX <= MBR.getMinX() || minX >= MBR.getMaxX() ||
		maxY <= MBR.getMinY() || minY >= MBR.getMaxY() );
    }
    
    public boolean contains(BoundingBox2D MBR) {
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
        if (!(o instanceof BoundingBox2D)) return false;
        BoundingBox2D other = (BoundingBox2D) o;
        return minX == other.minX && maxX == other.maxX &&
                minY == other.minY && maxY == other.maxY;
    }
}

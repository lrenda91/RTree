package it.lrenda.genericRtree.MBR;

/**
 *
 * @author Luigi
 */
public class BoundingBoxes2D {

    private BoundingBoxes2D() {
    }
    
    public static BoundingBox2D union(BoundingBox2D... boxes){
        double maxX = -Double.MAX_VALUE , maxY = -Double.MAX_VALUE ,
                minX = Double.MAX_VALUE , minY = Double.MAX_VALUE; 
        for (BoundingBox2D box : boxes){
            if (box == null) continue;
            if (box.getMaxX() > maxX)
                maxX = box.getMaxX();
            if (box.getMaxY() > maxY)
                maxY = box.getMaxY();
            if (box.getMinX() < minX)
                minX = box.getMinX();
            if (box.getMinY() < minY)
                minY = box.getMinY();
        }
        BoundingBox2D result = null;
        try{
           result = new BoundingBox2D(minX, maxX, minY, maxY);
        }
        catch(IllegalArgumentException ex){
            return null;
        }
        return result;
    }
    
    public static BoundingBox2D intersection(BoundingBox2D... boxes){
        double maxX = Double.MAX_VALUE , maxY = Double.MAX_VALUE ,
                minX = -Double.MAX_VALUE , minY = -Double.MAX_VALUE; 
        for (BoundingBox2D box : boxes){
            if (box == null) continue;
            if (box.getMaxX() < maxX)
                maxX = box.getMaxX();
            if (box.getMaxY() < maxY)
                maxY = box.getMaxY();
            if (box.getMinX() > minX)
                minX = box.getMinX();
            if (box.getMinY() > minY)
                minY = box.getMinY();
        }
        BoundingBox2D result = null;
        try{
           result = new BoundingBox2D(minX, maxX, minY, maxY);
        }
        catch(IllegalArgumentException ex){
            return null;
        }
        return result;
    }
    
    public static double overlap(BoundingBox2D toAdd, BoundingBox2D... boxes){
        double s = 0;
        for (BoundingBox2D b : boxes){
            if (b.equals(toAdd))
                continue;
            BoundingBox2D intersection = intersection(toAdd,b);
            s += (intersection == null ? 0 : intersection.area());
        }
        return s;
    }
    
}

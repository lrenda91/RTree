package it.lrenda.rtree.objects.MBR;

/**
 *
 * @author Luigi
 */
public class BoundingBoxes {

    private BoundingBoxes() {
    }
    
    public static BoundingBox union(BoundingBox... boxes){
        double maxX = -Double.MAX_VALUE , maxY = -Double.MAX_VALUE ,
                minX = Double.MAX_VALUE , minY = Double.MAX_VALUE; 
        for (BoundingBox box : boxes){
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
        BoundingBoxImpl result = null;
        try{
           result = new BoundingBoxImpl(minX, maxX, minY, maxY);
        }
        catch(IllegalArgumentException ex){
            return null;
        }
        return result;
    }
    
    public static BoundingBox intersection(BoundingBox... boxes){
        double maxX = Double.MAX_VALUE , maxY = Double.MAX_VALUE ,
                minX = -Double.MAX_VALUE , minY = -Double.MAX_VALUE; 
        for (BoundingBox box : boxes){
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
        BoundingBoxImpl result = null;
        try{
           result = new BoundingBoxImpl(minX, maxX, minY, maxY);
        }
        catch(IllegalArgumentException ex){
            return null;
        }
        return result;
    }
    
    public static double overlap(BoundingBox toAdd, BoundingBox... boxes){
        double s = 0;
        for (BoundingBox b : boxes){
            if (b.equals(toAdd))
                continue;
            BoundingBox intersection = intersection(toAdd,b);
            s += (intersection == null ? 0 : intersection.area());
        }
        return s;
    }
    
}

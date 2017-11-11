package it.lrenda.genericRtree.MBR;

public interface BoundingBox<P extends Point> {

    P center();

    boolean contains(P p);
        
}

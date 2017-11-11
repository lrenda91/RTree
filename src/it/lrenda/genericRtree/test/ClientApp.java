/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.test;

import it.lrenda.genericRtree.MBR.BoundingBox2D;
import it.lrenda.genericRtree.MBR.Point2D;
import it.lrenda.genericRtree.indiceAdattato.RstarTree;
import it.lrenda.genericRtree.indiceAdattato.SpatialObjectsIndex;
import it.lrenda.genericRtree.myObjects.*;

/**
 *
 * @author luigi
 */
public class ClientApp {
    
    private SpatialObjectsIndex index;

    public ClientApp(SpatialObjectsIndex index) {
        this.index = index;
    }

    public void add(CustomObject o){
        index.add(CustomObjectBox2DFactory.getBox(o), o);
    }
    
    public static void main(String[] args) {
        ClientApp app = new ClientApp(new RstarTree<BoundingBox2D, CustomObject>(1,2));
        app.add(new CustomCircle(new Point2D(4,5), 44));
        app.add(new CustomRectangle(new Point2D(15, 15), 30, 30));
        app.add(new CustomPicture(null, new Point2D(4, 5), 43, 65));
        System.out.println(app.index);
        System.out.println(app.index.exactMatchQuery(new BoundingBox2D(0,30,0,30)));
        System.out.println(app.index.pointQuery(new Point2D(10, 10)));
        System.out.println(app.index.rangeQuery(new BoundingBox2D(200, 1000, 200, 1000)));
        
    }
}

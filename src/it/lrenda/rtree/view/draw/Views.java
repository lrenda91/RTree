/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import java.util.HashMap;
import java.util.Map;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.geom.Circle;
import it.lrenda.rtree.objects.geom.Picture;
import it.lrenda.rtree.objects.geom.Rectangle;

/**
 *
 * @author Luigi
 */
public final class Views {
    
    private static HashMap<Class<? extends Drawable>,Drawer2D> map;

    private Views() {
    }
    
    private static Map<Class<? extends Drawable>,Drawer2D> getMap(){
        if (map == null){
            map = new HashMap<>();
            map.put(Circle.class, new CircleDrawer());
            map.put(Rectangle.class, new RectangleDrawer());
            map.put(BoundingBox.class, new BoundingBoxDrawer());
            map.put(Picture.class, new PictureDrawer());
        }
        return map;
    }
    
    public static Drawer2D get(Class<? extends Drawable> cl){
        return getMap().get(cl);
    }
    
    public static void install(Class<? extends Drawable> cl, Drawer2D drawer){
        getMap().put(cl, drawer);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view.draw;

import it.lrenda.rtree.index.rTree.Entry;
import it.lrenda.rtree.index.rTree.Node;
import it.lrenda.rtree.index.rTree.RTree;
import java.awt.Graphics2D;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class RTreeDrawer {

    private static final BoundingBoxDrawer bbDrawer = new BoundingBoxDrawer();

    public void draw(RTree tree, Graphics2D g2D){
        visit(tree.getRoot(),g2D);
    }

    private void visit(Node n, Graphics2D g2D) {
        g2D.setStroke(Drawer2D.dashedStroke);
        BoundingBox b = n.getMBR();
        bbDrawer.draw(b, g2D);
        for (Entry e : n.entries()) {
            visit(e,g2D);
        }
    }

    private void visit(Entry e, Graphics2D g2D) {
        g2D.setStroke(Drawer2D.dashedStroke);
        BoundingBox box = e.getMBR();
        g2D.drawString("R"+e.getId(), (int)box.getMinX(), (int)box.getMinY());
        bbDrawer.draw(box, g2D);
        if (e.child() == null){
            visit(e.object(), g2D);
        }
        else {
            visit(e.child(), g2D);
        }
    }

    private void visit(GraphicObject go, Graphics2D g2D) {
        g2D.setStroke(Drawer2D.normalStroke);
        Views.get(go.getClass()).draw(go, g2D);
    }
    
}

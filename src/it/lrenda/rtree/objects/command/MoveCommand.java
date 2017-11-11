/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.objects.command;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.objects.Point2D;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class MoveCommand implements Command {

    private GraphicObject receiver;
    private RTree index;
    private double x,y;

    public MoveCommand(GraphicObject receiver, RTree index, double x, double y) {
        this.receiver = receiver;
        this.index = index;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void invoke() {
        if (receiver == null)
            return;
        int id = index.getId(receiver);
        index.delete(receiver);
        receiver.moveTo(new Point2D(x, y));
        index.insert(id,receiver);
        receiver.notifyListeners();
    }
    
}

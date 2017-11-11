/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.objects.command;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class ZoomCommand implements Command {
    
    private GraphicObject receiver;
    private RTree index;
    private double factor;

    public ZoomCommand(GraphicObject receiver, RTree index, double factor) {
        this.receiver = receiver;
        this.index = index;
        this.factor = factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }
    
    @Override
    public void invoke() {
        if (receiver == null)
            return;
        int id = index.getId(receiver);
        index.delete(receiver);
        receiver.zoom(factor);
        index.insert(id,receiver);
        receiver.notifyListeners();
    }
    
    public void setReceiver(GraphicObject receiver){
        this.receiver = receiver;
    }
    
}

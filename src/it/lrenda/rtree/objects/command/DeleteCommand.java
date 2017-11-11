/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.objects.command;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author luigi
 */
public class DeleteCommand implements Command {
    
    private GraphicObject receiver;
    private RTree index;

    public DeleteCommand(GraphicObject receiver, RTree index) {
        this.receiver = receiver;
        this.index = index;
    }
    
    @Override
    public void invoke() {
        if (receiver == null)
            return;
        index.delete(receiver);
        receiver.notifyListeners();
    }

    public void setReceiver(GraphicObject receiver) {
        this.receiver = receiver;
    }
    
}

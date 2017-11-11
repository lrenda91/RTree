/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.objects.command.DeleteCommand;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author luigi
 */
public class DeleteButton extends CommandButton {

    private GraphicObject receiver;
    
    public DeleteButton(RTree index) {
        super(new DeleteCommand(null, index));
        setIcon(AppFrame.getImage("delete-icon.png"));
    }

    public void setSubject(GraphicObject receiver) {
        ((DeleteCommand) command).setReceiver(receiver);
    }
    
}

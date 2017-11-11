/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.lrenda.rtree.objects.command;

import it.lrenda.rtree.index.rTree.RTree;
import java.util.Random;
import it.lrenda.rtree.objects.GraphicObject;
import it.lrenda.rtree.objects.Point2D;

/**
 *
 * @author Luigi
 */
public abstract class CreateCommand<T extends GraphicObject> implements Command {

    private RTree tree;
    
    protected abstract T getNewObject(double xPos, double yPos);

    public CreateCommand(RTree tree) {
        this.tree = tree;
    }
    
    @Override 
    public void invoke(){
        double x = new Random().nextInt(1000);
        double y = new Random().nextInt(1000);
        T receiver = getNewObject(x,y);
        receiver.moveTo(new Point2D(x, y));
        tree.add(receiver);
        receiver.notifyListeners();
    }
    
}

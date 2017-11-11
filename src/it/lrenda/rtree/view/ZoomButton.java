/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.objects.command.ZoomCommand;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class ZoomButton extends CommandButton {

    public ZoomButton(double factor,RTree index) {
        super(new ZoomCommand(null,index,factor));
        String URL = ((factor > 1) ? "zoom-in.png" : "zoom-out.png");
        setIcon(AppFrame.getImage(URL));
    }
    
    public void setFactor(double factor){
        ((ZoomCommand) command).setFactor(factor);
    }
    
    public void setSubject(GraphicObject go){
        ((ZoomCommand) command).setReceiver(go);
    }
    
}

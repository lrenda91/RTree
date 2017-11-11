/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import it.lrenda.rtree.index.rTree.RTree;

import java.awt.GridLayout;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import it.lrenda.rtree.objects.Point2D;
import it.lrenda.rtree.objects.command.CreateCommand;
import it.lrenda.rtree.objects.geom.Circle;
import it.lrenda.rtree.objects.GraphicObject;
import it.lrenda.rtree.objects.geom.Picture;
import it.lrenda.rtree.objects.geom.Rectangle;

/**
 *
 * @author Luigi
 */
public final class ControlsPanel extends JPanel {

    private ZoomButton zoomIn, zoomOut;
    private DeleteButton delete;
    private CommandButton createCircle, createRectangle, createImage;
    JLabel coordLabel = new JLabel("0,0");
    
    public ControlsPanel(final RTree tree, final DrawingPanel panel) {
        super(new GridLayout(1,3));
        zoomIn = new ZoomButton(1.333,tree);
        zoomOut = new ZoomButton(0.75,tree);
        delete = new DeleteButton(tree);
        createCircle = new CommandButton(new CreateCommand(tree) {
            @Override
            protected GraphicObject getNewObject(double xPos, double yPos) {
                double r = new Random().nextInt(20)+10;
                Circle c = new Circle(new Point2D(xPos,yPos), r);
                c.addGraphicObjectListener(panel);
                return c;
            }
        }) {};
        createCircle.setIcon(AppFrame.getImage("circle-icon.png"));

        createRectangle = new CommandButton(new CreateCommand(tree) {
            @Override
            protected GraphicObject getNewObject(double xPos, double yPos) {
                Rectangle c = new Rectangle(new Point2D(xPos, yPos), 30, 20);
                c.addGraphicObjectListener(panel);
                return c;
            }
        }) {};
        createRectangle.setIcon(AppFrame.getImage("rectangle-icon.png"));
        
        createImage = new CommandButton(new CreateCommand(tree) {
            @Override
            protected GraphicObject getNewObject(double xPos, double yPos) {
                ImageIcon i = AppFrame.getImage("logo.png");
                Picture p = new Picture(i, new Point2D(xPos, yPos), 35, 50);
                p.addGraphicObjectListener(panel);
                return p;
            }
        }) {};
        createImage.setIcon(AppFrame.getImage("image-icon.png"));
          
        add(zoomIn);
        add(zoomOut);
        add(createCircle);
        add(createRectangle);
        add(createImage);
        add(delete);
        add(coordLabel);
        setSubject(null);
    }
    
    public void setSubject(GraphicObject go){
        zoomIn.setSubject(go);
        zoomOut.setSubject(go);
        delete.setSubject(go);
        zoomIn.setEnabled(go != null);
        zoomOut.setEnabled(go != null);
        delete.setEnabled(go != null);
    }
    
}

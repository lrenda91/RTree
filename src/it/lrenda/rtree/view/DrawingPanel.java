/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import it.lrenda.rtree.objects.GraphicObjectListener;
import it.lrenda.rtree.index.rTree.RTree;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;
import it.lrenda.rtree.objects.MBR.BoundingBoxImpl;
import it.lrenda.rtree.objects.command.MoveCommand;
import it.lrenda.rtree.objects.GraphicObject;
import it.lrenda.rtree.view.draw.RTreeDrawer;
import it.lrenda.rtree.view.draw.ShadowDrawer;
import it.lrenda.rtree.view.draw.Views;

/**
 *
 * @author Luigi
 */
public class DrawingPanel extends JPanel implements GraphicObjectListener, MouseListener, MouseMotionListener {

    private static final RTreeDrawer treeDrawer = new RTreeDrawer();
    private static final ShadowDrawer shadowDrawer = new ShadowDrawer();
    private ControlsPanel controller;
    private GraphicObject selected;
    private RTree index;
    private boolean drawTree = false, moving = false;

    public DrawingPanel(RTree index, ControlsPanel controller) {
        this.index = index;
        this.controller = controller;
        setPreferredSize(new Dimension(1000,1000));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public GraphicObject getSelected() {
        return selected;
    }

    public RTree getIndex() {
        return index;
    }

    public void setController(ControlsPanel controller) {
        this.controller = controller;
    }

    public void setSelected(GraphicObject selected) {
        this.selected = selected;
    }

    public void changeDrawAll() {
        this.drawTree = !this.drawTree;
        repaint();
    }
    
    @Override
    public void update(GraphicObject go) {
        repaint();
        controller.setSubject(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (drawTree){
            treeDrawer.draw(index,g2D);
            return;
        }
        java.awt.Rectangle frame = getVisibleRect();
        BoundingBoxImpl box = new BoundingBoxImpl(frame.getMinX(),frame.getMaxX(),frame.getMinY(),frame.getMaxY());
        List<GraphicObject> query = index.rangeQuery(box);
        for (GraphicObject go : query){
            Views.get(go.getClass()).draw(go, g2D);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        List<GraphicObject> query = index.pointQuery(e.getX(),e.getY());
        selected = ( query.isEmpty() ? null : query.get(0) );
        controller.setSubject(selected);
        if (selected != null){
            selected.addGraphicObjectListener(this);
            System.out.println("found "+selected+" in entry "+index.findLeaf(selected));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selected != null && moving){
            new MoveCommand(selected, index, e.getX(), e.getY()).invoke();
            moving = false;
            controller.setSubject(null);
            System.out.println(index);
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selected == null)
            return;
        if (!moving)
            moving = true;
        Graphics2D g2D = (Graphics2D) getGraphics();
        shadowDrawer.draw(selected, g2D, e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        controller.coordLabel.setText(e.getX()+","+e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

}

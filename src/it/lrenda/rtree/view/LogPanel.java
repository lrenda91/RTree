/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import it.lrenda.rtree.objects.GraphicObjectListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class LogPanel extends JPanel implements GraphicObjectListener {

    private JTextArea logArea = new JTextArea(5,30);

    public LogPanel() {
        setSize(400,100);
        add(new JScrollPane(logArea));
        setVisible(true);
    }
    
    @Override
    public void update(GraphicObject obj) {
        logArea.append(obj == null ? "Null" : obj.toString());
        logArea.append("\n");
    }
    
}

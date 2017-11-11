/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import it.lrenda.rtree.objects.command.Command;

/**
 *
 * @author Luigi
 */
public abstract class CommandButton extends JButton {
    
    protected Command command;
    
    public CommandButton(final Command command) {
        this.command = command;
        setPreferredSize(new Dimension(50,40));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                command.invoke();
            }
        });
    }
    
}

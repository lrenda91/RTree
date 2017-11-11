package it.lrenda.rtree.view;

import it.lrenda.rtree.index.rTree.RTree;
import it.lrenda.rtree.index.rTree.RstarTree;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Luigi
 */
public class AppFrame extends JFrame {
    
    public static ImageIcon getImage(String fileName){
        return new ImageIcon(AppFrame.class.getResource("/it/lrenda/rtree/view/images/" +fileName));
    }
    
    public AppFrame(RTree idx) {
        super("R-tree Demo");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        final DrawingPanel view = new DrawingPanel(idx,null);
        final ControlsPanel controller = new ControlsPanel(idx,view);
        view.setController(controller);
        final JCheckBox entireIndex = new JCheckBox("Visualizza Bounding Boxes");
        entireIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                view.changeDrawAll();
            }
        });
        
        JScrollPane pane = new JScrollPane(view);
        pane.setPreferredSize(new Dimension(500, 500));
        pane.setWheelScrollingEnabled(true);
        getContentPane().add(controller,BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.CENTER);
        
        getContentPane().add(entireIndex,BorderLayout.CENTER);
        getContentPane().add(controller.coordLabel,BorderLayout.SOUTH);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setSize(600, 630);
        int xLoc = (screen.width - getSize().width)/2;
        int yLoc = (screen.height - getSize().height)/2;
        setLocation(xLoc, yLoc);
    }
    
    public static void main(String[] args) {
        RTree tree = new RstarTree(2, 5);
        new AppFrame(tree).setVisible(true);
    }
    
}

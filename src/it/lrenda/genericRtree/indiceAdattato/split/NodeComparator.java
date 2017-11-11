/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.indiceAdattato.split;

import it.lrenda.genericRtree.indiceAdattato.*;
import java.util.Comparator;

/**
 *
 * @author Luigi
 */
public class NodeComparator implements Comparator<Node> {

    private Entry toAdd;

    public NodeComparator(Entry toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public int compare(Node n1, Node n2) {
        double enlargementE1 = n1.getMBR().enlargementArea(toAdd.getMBR());
        double enlargementE2 = n2.getMBR().enlargementArea(toAdd.getMBR());
        if (enlargementE1 < enlargementE2)
            return -1;
        else if (enlargementE1 > enlargementE2)
            return +1;
        double area1 = n1.getMBR().area();
        double area2 = n2.getMBR().area();
        if (area1 < area2)
            return -1;
        else if (area1 > area2)
            return +1;
        int size1 = n1.size();
        int size2 = n2.size();
        if (size1 < size2)
            return -1;
        else if (size1 > size2)
            return +1;
        return 0;
    }

}

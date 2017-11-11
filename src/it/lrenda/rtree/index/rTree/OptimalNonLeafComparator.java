/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.rtree.index.rTree;

import java.util.List;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.MBR.BoundingBoxes;

/**
 *
 * @author Luigi
 */
public class OptimalNonLeafComparator extends NonLeafComparator {

    private List<Entry> nodeEntries;
    
    public OptimalNonLeafComparator(Entry toAdd, List<Entry> entries) {
        super(toAdd);
        nodeEntries = entries;
    }

    public void setNodeEntries(List<Entry> nodeEntries) {
        this.nodeEntries = nodeEntries;
    }

    @Override
    public int compare(Entry e1, Entry e2) {
        BoundingBox union1 = e1.getMBR().union(toAdd.getMBR());
        BoundingBox union2 = e2.getMBR().union(toAdd.getMBR());
        BoundingBox[] entriesArray = new BoundingBox[nodeEntries.size()];
        for (int i=0;i<entriesArray.length;i++)
            entriesArray[i] = nodeEntries.get(i).getMBR();
        double overlap1 = BoundingBoxes.overlap(union1, entriesArray);
        double overlap2 = BoundingBoxes.overlap(union2, entriesArray);
        if (overlap1 < overlap2)
            return -1;
        else if (overlap1 > overlap2)
            return +1;
        return super.compare(e1, e2);
    }
    
}

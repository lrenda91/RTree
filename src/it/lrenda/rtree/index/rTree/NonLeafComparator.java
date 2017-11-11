package it.lrenda.rtree.index.rTree;

import java.util.Comparator;

/**
 *
 * @author Luigi
 */
public class NonLeafComparator implements Comparator<Entry> {

    protected Entry toAdd;

    public NonLeafComparator(Entry toAdd) {
        this.toAdd = toAdd;
    }

    public void setEntryToAdd(Entry toAdd) {
        this.toAdd = toAdd;
    }
       
    @Override
    public int compare(Entry e1, Entry e2) {
        double enlargementE1 = e1.getMBR().enlargementArea(toAdd.getMBR());
        double enlargementE2 = e2.getMBR().enlargementArea(toAdd.getMBR());
        if (enlargementE1 < enlargementE2)
            return -1;
        else if (enlargementE1 > enlargementE2)
            return +1;
        double area1 = e1.getMBR().area();
        double area2 = e2.getMBR().area();
        if (area1 < area2)
            return -1;
        else if (area1 > area2)
            return +1;
        return 0;
    }
    
}

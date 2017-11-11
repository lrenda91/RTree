package it.lrenda.genericRtree.indiceAdattato.split;

import it.lrenda.genericRtree.MBR.BoundingBox2D;
import it.lrenda.genericRtree.MBR.BoundingBoxes2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import it.lrenda.genericRtree.indiceAdattato.Entry;
import it.lrenda.genericRtree.indiceAdattato.Node;

/**
 *
 * @author Luigi
 */
public class LinearSplitting implements SplittingStrategy {

    @Override
    public Node split(Node fullLeaf, Entry entry, int minSize) {
        int maxSize = fullLeaf.size();
        Node newLeaf = new Node(null);
        ArrayList<Entry> entries = new ArrayList<Entry>(fullLeaf.entries());   
        entries.add(entry);     //taglia (M+1)
        Entry[] seeds = linearPickSeeds(entries);
        
        entries.remove(seeds[0]);
        entries.remove(seeds[1]);
        fullLeaf.clear();
        fullLeaf.addEntry(seeds[0]);
        newLeaf.addEntry(seeds[1]);
        
        while (!entries.isEmpty()){
            int s = entries.size();
            if (fullLeaf.size() == (minSize - s)){
                for (Entry e : entries)
                    fullLeaf.addEntry(e);
                break;
            }
            else if (newLeaf.size() == (minSize - s)){
                for (Entry e : entries)
                    newLeaf.addEntry(e);
                break;
            }
            Entry next = entries.remove(new Random().nextInt(entries.size()));
            if (new NodeComparator(next).compare(fullLeaf, newLeaf) < 0){
                fullLeaf.addEntry(next);
            }
            else{
                newLeaf.addEntry(next);
            }
        }
        //System.out.println("Scelti: N="+fullLeaf+" M="+newLeaf);
        return newLeaf;
    }
    
    private static Entry[] linearPickSeeds(List<Entry> entries){
        Entry[] result = new Entry[2];
        BoundingBox2D[] boxes = new BoundingBox2D[entries.size()];
        double x1 = -Double.MAX_VALUE , x2 = Double.MAX_VALUE , y1 = -Double.MAX_VALUE , y2 = Double.MAX_VALUE;
        Entry e1x = null, e2x = null, e1y = null, e2y = null; int i=0;
        for (Entry e : entries){
            BoundingBox2D bb = e.getMBR();
            boxes[i++] = bb;
            if (bb.getMinX() > x1){
                x1 = bb.getMinX();
                e1x = e;
            }
            if (bb.getMaxX() < x2){
                x2 = bb.getMaxX();
                e2x = e;
            }
            if (bb.getMinY() > y1){
                y1 = bb.getMinY();
                e1y = e;
            }
            if (bb.getMaxY() < y2){
                y2 = bb.getMaxY();
                e2y = e;
            }
        }
        double separationX = x1-x2;
        double separationY = y1-y2;
        BoundingBox2D union = BoundingBoxes2D.union(boxes);
        double normSeparationX = separationX / union.width();
        double normSeparationY = separationY / union.height();
        if (normSeparationX > normSeparationY){
            result[0] = e1x;
            result[1] = e2x;
        }
        else{
            result[0] = e1y;
            result[1] = e2y;
        }
        return result;
    }
    
}

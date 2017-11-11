package it.lrenda.genericRtree.indiceAdattato.split;

import it.lrenda.genericRtree.MBR.BoundingBox2D;
import it.lrenda.genericRtree.MBR.BoundingBoxes2D;
import java.util.ArrayList;
import it.lrenda.genericRtree.indiceAdattato.Entry;
import it.lrenda.genericRtree.indiceAdattato.Node;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luigi
 */
public class ExhaustiveSplitting implements SplittingStrategy {

    @Override
    public Node split(Node fullLeaf, Entry entry, int minSize) {
        //System.out.println("Split di "+fullLeaf+" con la entry "+entry+" :");
        int maxSize = fullLeaf.size();
        Node newLeaf = new Node(null);
        ArrayList<Entry> entries = new ArrayList<Entry>(fullLeaf.entries());   
        entries.add(entry);     //taglia (M+1)
        double minArea = Double.MAX_VALUE;
        for (List<Entry> entriesNode1 : combinazioni(entries,maxSize)){
            List<Entry> entriesNode2 = new ArrayList<Entry>(entries);
            entriesNode2.removeAll(entriesNode1);
            if (entriesNode1.size() < minSize || entriesNode2.size() < minSize)
                continue;
            double areaSum = totArea(entriesNode1) + totArea(entriesNode2);
            if (areaSum < minArea){
                minArea = areaSum;
                fullLeaf.setEntries(entriesNode1);
                newLeaf.setEntries(entriesNode2);
            }
        }
        //System.out.println("Scelti: N="+fullLeaf+" M="+newLeaf);
        return newLeaf;
    }
    
    private static List<List<Entry>> combinazioni(ArrayList<Entry> entries, int max) {
        List<List<Entry>> result = new LinkedList<List<Entry>>();
        combRicorsivo(new ArrayList<Entry>(), result, 0, entries, max);
        return result;
    }

    private static void combRicorsivo(List<Entry> list, List<List<Entry>> result, int idx,List<Entry> elem, int max) {
        if (list.size() == Math.ceil((double)max / 2) ) {
            return;
        }
        for (int j = idx; j < max; j++) {
            list.add(elem.get(j));
            if (!result.contains(list)) {
                result.add(new ArrayList<Entry>(list));
            }
            for (int i = 1; (j + i) < max; i++) {
                combRicorsivo(new ArrayList<Entry>(list), result, j + i, elem, max);
            }
            list.remove(list.size()-1);
        }
    }
    
    private static double totArea(List<Entry> entries){
        BoundingBox2D[] boxes = new BoundingBox2D[entries.size()]; int i=0;
            for (Entry en : entries){
                boxes[i++] = en.getMBR();
            }
        return BoundingBoxes2D.union(boxes).area();
    }
    
}

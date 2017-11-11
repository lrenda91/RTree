package it.lrenda.rtree.index.rTree.split;

import it.lrenda.rtree.index.rTree.Entry;
import it.lrenda.rtree.index.rTree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.lrenda.rtree.objects.MBR.BoundingBoxes;

/**
 *
 * @author Luigi
 */
public class OptimalSplitting implements SplittingStrategy {

    @Override
    public Node split(Node fullLeaf, Entry entry, int minSize) {
        Node newLeaf = new Node(null);
        int M = fullLeaf.size();
        List<Entry> entries = new ArrayList<>(fullLeaf.entries());
        entries.add(entry);
        List<Entry> list = chooseSplitAxis(entries, minSize, M);
        Distribution d = chooseSplitIndex(list, minSize, M);
        fullLeaf.setEntries(d.n1.entries());
        newLeaf.setEntries(d.n2.entries());
        return newLeaf;
    }
    
    private static List<Entry> chooseSplitAxis(List<Entry> entries, int m, int M){
        List<Entry> res = entries;
        double minMarginSum = Double.MAX_VALUE;
        double currentMarginSum;
        Collections.sort(entries, new Comparator<Entry>(){
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.getMBR().getMinX() < e2.getMBR().getMinX())
                    return -1;
                if (e1.getMBR().getMinX() > e2.getMBR().getMinX())
                    return +1;
                return 0;
            }      
        });
        currentMarginSum = computeMarginSum(entries, m, M);
        if (currentMarginSum < minMarginSum){
            minMarginSum = currentMarginSum;
            res = entries;
        }
        Collections.sort(entries, new Comparator<Entry>(){
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.getMBR().getMaxX() < e2.getMBR().getMaxX())
                    return -1;
                if (e1.getMBR().getMaxX() > e2.getMBR().getMaxX())
                    return +1;
                return 0;
            }      
        });
        currentMarginSum = computeMarginSum(entries, m, M);
        if (currentMarginSum < minMarginSum){
            minMarginSum = currentMarginSum;
            res = entries;
        }
        Collections.sort(entries, new Comparator<Entry>(){
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.getMBR().getMinY() < e2.getMBR().getMinY())
                    return -1;
                if (e1.getMBR().getMinY() > e2.getMBR().getMinY())
                    return +1;
                return 0;
            }      
        });
        currentMarginSum = computeMarginSum(entries, m, M);
        if (currentMarginSum < minMarginSum){
            minMarginSum = currentMarginSum;
            res = entries;
        }
        Collections.sort(entries, new Comparator<Entry>(){
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.getMBR().getMaxY() < e2.getMBR().getMaxY())
                    return -1;
                if (e1.getMBR().getMaxY() > e2.getMBR().getMaxY())
                    return +1;
                return 0;
            }      
        });
        currentMarginSum = computeMarginSum(entries, m, M);
        if (currentMarginSum < minMarginSum){
            minMarginSum = currentMarginSum;
            res = entries;
        }
        return res;
    }
    
    private static double computeMarginSum(List<Entry> entries, int m, int M){
        double sum = 0;
        int k = 1;
        int totDistributions = (M-2*m+2);
        while (k <= totDistributions){
            Distribution d = getPartition(entries, k, m);
            sum += (d.n1.getMBR().margin() + d.n2.getMBR().margin());
            k++;
        }
        return sum;
    }
    
    
    private static Distribution getPartition(List<Entry> entries, int k, int m){
        Distribution d = new Distribution();
        for (int i=0;i<entries.size();i++){
            if (i < (m-1+k)){
                d.addEntryToNode1(entries.get(i));
            }
            else{
                d.addEntryToNode2(entries.get(i));
            }
        }
        return d;
    }
    
    
    private static Distribution chooseSplitIndex(List<Entry> entries, int m, int M){
        double minOverlap = Double.MAX_VALUE;
        double minArea = Double.MAX_VALUE;
        Distribution res = null;
        int k = 1;
        int totDistributions = (M-2*m+2);
        while (k <= totDistributions){
            Distribution d = getPartition(entries, k, m);
            double overlap = BoundingBoxes.overlap(d.n1.getMBR(), d.n2.getMBR());
            if(overlap <= minOverlap){
                if(overlap == minOverlap){
                    double area1 = d.n1.getMBR().area() + d.n2.getMBR().area();
                    double area2 = res.n1.getMBR().area() + res.n2.getMBR().area();
                    minArea = Math.min(area1, area2);
                    if(minArea == area1){
                        res = d;
                    }
                }
                else{
                    minOverlap = overlap; 
                    res = d;
                }
            }
            k++; 
        }
        return res;
    }
    
    
    
    
    private static class Distribution {
        Node n1,n2;
        Distribution(){
            n1 = new Node(null);
            n2 = new Node(null);
        }
        void addEntryToNode1(Entry e){
            n1.addEntry(e);
        }
        void addEntryToNode2(Entry e){
            n2.addEntry(e);
        }

        @Override
        public String toString() {
            return "["+n1.toString()+","+n2.toString()+"]";
        }
    }
    
}

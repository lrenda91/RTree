package it.lrenda.genericRtree.indiceAdattato.split;

import it.lrenda.genericRtree.indiceAdattato.Entry;
import it.lrenda.genericRtree.indiceAdattato.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luigi
 */
public class QuadraticSplitting implements SplittingStrategy {

    @Override
    public Node split(Node fullLeaf, Entry Entry1, int minSize) {
        //System.out.println("Split di "+fullLeaf+" con la Entry1 "+Entry1+" :");
        Node newLeaf = new Node(null);
        ArrayList<Entry> entries = new ArrayList<Entry>(fullLeaf.entries());   
        entries.add(Entry1);     //taglia (M+1)
        Entry[] seeds = pickSeeds(entries);
        
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
            Entry next = pickNext(entries, fullLeaf, newLeaf);
            entries.remove(next);
            if (new NodeComparator(next).compare(fullLeaf, newLeaf) < 0){
                fullLeaf.addEntry(next);
            }
            else{
                newLeaf.addEntry(next);
            }
        }
        //System.out.println("Scelti N:"+fullLeaf+" M:"+newLeaf);
        return newLeaf;
    }
    
    private static Entry[] pickSeeds(List<Entry> entries){
        Entry[] seeds = new Entry[2];
        double maxDeadSpace = -Double.MAX_VALUE;
        for (int i=0;i<entries.size();i++){
            for (int j=0;j<entries.size();j++){
                if (i!=j){
                    Entry e1 = entries.get(i);
                    Entry e2 = entries.get(j);
                    double deadSpace = e1.getMBR().union(e2.getMBR()).area() - (e1.getMBR().area()+e2.getMBR().area());
                    if (deadSpace > maxDeadSpace){
                        maxDeadSpace = deadSpace;
                        seeds[0] = e1;
                        seeds[1] = e2;
                    }
                }
            }
        }
        return seeds;
    }
    
    private static Entry pickNext(List<Entry> remaining,Node l1,Node l2){        
        Entry result = null;
        double maxDiff = -1;
        for (Entry e : remaining){
            double enlargement1 = l1.getMBR().enlargementArea(e.getMBR());
            double enlargement2 = l2.getMBR().enlargementArea(e.getMBR());
            double diff = Math.abs(enlargement1 - enlargement2);
            if (diff > maxDiff){
                maxDiff = diff;
                result = e;
            }
        }
        return result;
    }

}

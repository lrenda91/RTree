package it.lrenda.genericRtree.indiceAdattato;

import it.lrenda.genericRtree.indiceAdattato.split.OptimalSplitting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.lrenda.genericRtree.MBR.BoundingBox;
import it.lrenda.genericRtree.MBR.BoundingBox2D;

/**
 *
 * @author Luigi
 */
public class RstarTree<K extends BoundingBox2D,V> extends RTree<K,V> {

    private int[] overflowTreatmentCalls = new int[100];
    private int p;
    private OptimalNonLeafComparator optimalComparator;
    
    public RstarTree(int m, int M) {
        super(m, M, new OptimalSplitting());
        p = (int) Math.ceil(0.3*(double)M);
        optimalComparator = new OptimalNonLeafComparator(null, null);
    }

    @Override
    public void insert(int id, K MBR, V obj){
        if (id == 0){
            id = ++count;
        }
        Entry e = new Entry(id, MBR, obj);
        Node leaf = chooseLeaf(e);
        if (leaf.size() < M){
            leaf.addEntry(e);
        }
        else{
            overflowTreatment(leaf, e);
        }
    }

    @Override
    protected Node chooseLeaf(Entry e) {
        optimalComparator.setEntryToAdd(e);
        comparator.setEntryToAdd(e);
        Node n = root;
        while (!n.isLeaf()){
            Entry nextEntry = null;
            if (n.entries().get(0).child().isLeaf()){   //penultimo livello
                optimalComparator.setNodeEntries(n.entries());
                nextEntry = Collections.min(n.entries(),optimalComparator);
            }
            else{
                nextEntry = Collections.min(n.entries(),comparator);
            }
            n = nextEntry.child();
        }
        return n;
    }
    
    private void overflowTreatment(Node fullLeaf, Entry e){
        int level = fullLeaf.level();
        overflowTreatmentCalls[level]++;
        if (level != 0 && overflowTreatmentCalls[level] == 1){
            reInsert(fullLeaf,e);
        }
        else{
            Node splitLeaf = strategy.split(fullLeaf, e, m);
            adjustTree(fullLeaf, splitLeaf);
            updateLevels();
        }
    }
    
    private void reInsert(final Node fullLeaf, Entry e){
        fullLeaf.addEntry(e);
        List<Entry> entries = new ArrayList<Entry>(fullLeaf.entries());
        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                BoundingBox leafBox = fullLeaf.getMBR();
                double distance1 = leafBox.center().distance(e1.getMBR().center());
                double distance2 = leafBox.center().distance(e2.getMBR().center());
                if (distance1 > distance2)
                    return -1;
                if (distance1 < distance2)
                    return +1;
                return 0;
            }
        });
        List<Entry> toReinsert = entries.subList(0, p);
        for (Entry en : toReinsert)
            fullLeaf.deleteEntry(en);
        for (Entry en : toReinsert)
            insert(en.getId(), (K) en.getMBR(), (V) en.object());
    }
    
    private void updateLevels(){
        updateLevelsRec(root,0);
    }
    
    private static void updateLevelsRec(Node n,int level){
        if (n == null)
            return;
        n.setLevel(level);
        for (Entry e : n.entries()){
            updateLevelsRec(e.child(), level+1);
        }
    }

}

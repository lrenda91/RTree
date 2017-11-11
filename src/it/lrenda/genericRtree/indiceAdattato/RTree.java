package it.lrenda.genericRtree.indiceAdattato;

import it.lrenda.genericRtree.MBR.Point2D;
import it.lrenda.genericRtree.indiceAdattato.split.SplittingStrategy;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.lrenda.genericRtree.MBR.BoundingBox2D;

/**
 *
 * @author Luigi
 */
public class RTree<K extends BoundingBox2D,V> implements SpatialObjectsIndex<K,V> {

    protected Node root;
    protected int m,M,count;
    protected SplittingStrategy strategy;
    protected NonLeafComparator comparator;

    public RTree(int m, int M, SplittingStrategy strategy) {
        if (m > (M/2))
            throw new IllegalArgumentException();
        this.m = m;
        this.M = M;
        this.strategy = strategy;
        this.count = 0;
        this.root = new Node(null);
        this.comparator = new NonLeafComparator(null);
    }

    public Node getRoot() {
        return root;
    }

    public void setStrategy(SplittingStrategy strategy) {
        this.strategy = strategy;
    }
    
    @Override
    public List<Entry> pointQuery(Point2D point) {
        List<Entry> result = new LinkedList<Entry>();
        pointQueryRicorsivo(root, result, point);
        return result;
    }

    private static void pointQueryRicorsivo(Node Node1, List<Entry> list, Point2D point) {
        if (Node1 == null) {
            return;
        }
        for (Entry e : Node1.entries()) {
            if (Node1.isLeaf()){
                if (e.getMBR().contains(point))
                    list.add(e);
            }
            else if (e.getMBR().contains(point)) {
                pointQueryRicorsivo(e.child(), list, point);
            }
        }
    }
    
    @Override
    public List<Entry> rangeQuery(K box) {
        List<Entry> result = new LinkedList<Entry>();
        rangeQueryRicorsivo(root, result, box);
        return result;
    }
    
    private void rangeQueryRicorsivo(Node Node1, List<Entry> list, K box) {
        if (Node1 == null) {
            return;
        }
        for (Entry e : Node1.entries()) {
            if (e.getMBR().intersects(box)) {
                if (Node1.isLeaf())
                    list.add(e);
                else
                    rangeQueryRicorsivo(e.child(), list, box);
            }
        }
    }

    @Override
    public V exactMatchQuery(K MBR) {
        Entry leaf = findLeaf(MBR);
        return (leaf == null ? null : (V) leaf.object());
    }

    @Override
    public void add(K MBR, V value) {
        if (value == null)
            return;
        insert(++count, MBR, value);
    }
    
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
            Node splitLeaf = strategy.split(leaf, e, m);
            adjustTree(leaf, splitLeaf);
        }
    }
    
    protected Node chooseLeaf(Entry e){
        comparator.setEntryToAdd(e);
        Node n = root;
        while (!n.isLeaf()){
            n = Collections.min(n.entries(), comparator).child();
        }
        return n;
    }
    
    protected void adjustTree(Node N, Node NN){
        boolean splitPerformed = (NN != null);
        if (!splitPerformed){
            return;
        }
        if (N == root){
            root = new Node(null);
            root.addEntry(new Entry(++count,N));
            root.addEntry(new Entry(++count,NN));
            return;
        }
        Entry Enn = new Entry(++count, NN); 
        Node p = N.parentNode();
        Node pp = null;
        if (p.size() < M){
            p.addEntry(Enn);
        }
        else{
            pp = strategy.split(p, Enn, m);
        }
        adjustTree(p, pp);
    }
    
    @Override
    public void delete(K MBR) {
        if (MBR == null)
            return;
        Entry leafEntry1 = findLeaf(MBR);
        if (leafEntry1 == null)
            return;
        Node n = leafEntry1.belongingNode();
        n.deleteEntry(leafEntry1);       
        List<Entry> toReInsert = condenseTree(n);
        for (Entry e : toReInsert){
            if (e.child() == null){
                insert(e.getId(), (K) e.getMBR(), (V) e.object());
            }
            else{
                List<Entry> leaves = new LinkedList<Entry>();
                leaves(e.child(), leaves);
                for (Entry e1 : leaves){
                    insert(e1.getId(), (K) e1.getMBR(), (V) e1.object());
                }
            }
        }
        if (root.size()==1){
            Node uniqueEntry1Child = root.entries().get(0).child();
            if (uniqueEntry1Child != null){
                uniqueEntry1Child.setParentEntry(null);
                root = uniqueEntry1Child;
            }
        }
    }

    public Entry findLeaf(K leafMBR){
        return findLeafRec(root, leafMBR);
    }
    
    private Entry findLeafRec(Node n, K leafMBR){
        if (n.isLeaf()){
            for (Entry e : n.entries()){
                if (e.getMBR().equals(leafMBR))
                    return e;
            }
            return null;
        }
        for (Entry e : n.entries()){
            if (e.getMBR().intersects(leafMBR)){
                Entry recursiveSearch = findLeafRec(e.child(), leafMBR);
                if (recursiveSearch == null)
                    continue;
                else
                    return recursiveSearch;
            }
        }
        return null;
    }
    
    private List<Entry> condenseTree(Node Node1){
        List<Entry> toReInsert = new LinkedList<Entry>();
        while (Node1 != root){
            Entry parentEntry1 = Node1.parentEntry();
            Node parentNode1 = Node1.parentNode();
            if (Node1.size() < m){
                parentNode1.deleteEntry(parentEntry1);
                parentEntry1.setChild(null);
                toReInsert.addAll(Node1.entries());
            }
            Node1 = Node1.parentNode();
        }
        return toReInsert;
    }
    
    @Override
    public void clear() {
        root = new Node(null);
    }
    
    private void leaves(Node n,List<Entry> list){
        if (n.isLeaf()){
            list.addAll(n.entries());
        }
        else{
            for (Entry e : n.entries()){
                leaves(e.child(), list);
            }
        }
    }
    public int getId(K MBR){
        Entry e = findLeaf(MBR);
        return (e == null ? 0 : e.getId());
    }
    
    @Override
    public String toString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        toStringRec(root,out,0);
        return baos.toString();
    }
    
    private void toStringRec(Node n,PrintStream out,int level){
        if (n == root){
            out.print("root:");
        }
        out.println(n);
        int nextLevel = level+1;
        for (Entry e : n.entries()){
            for (int i=0;i<nextLevel;i++){
                out.print("\t");
            }
            if (e.belongingNode().isLeaf())
                out.print(e+" (leaf)->"+e.object());
            else{
                out.print("R"+e.getId()+".child: ");
                toStringRec(e.child(), out, nextLevel);
            }
            out.println();
        }
    }
 
}

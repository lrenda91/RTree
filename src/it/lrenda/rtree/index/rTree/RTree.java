package it.lrenda.rtree.index.rTree;

import it.lrenda.rtree.index.SpatialObjectsIndex;
import it.lrenda.rtree.index.rTree.split.SplittingStrategy;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.Point2D;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class RTree implements SpatialObjectsIndex {

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
    public List<GraphicObject> pointQuery(double x, double y) {
        List<GraphicObject> result = new LinkedList<>();
        Point2D point = new Point2D(x, y);
        pointQueryRicorsivo(root, result, point);
        return result;
    }

    private static void pointQueryRicorsivo(Node node, List<GraphicObject> list, Point2D point) {
        if (node == null) {
            return;
        }
        for (Entry entry : node.entries()) {
            if (node.isLeaf()){
                if (entry.object().contains(point))
                    list.add(entry.object());
            }
            else if (entry.getMBR().contains(point)) {
                pointQueryRicorsivo(entry.child(), list, point);
            }
        }
    }
    
    @Override
    public List<GraphicObject> rangeQuery(BoundingBox box) {
        List<GraphicObject> result = new LinkedList<>();
        rangeQueryRicorsivo(root, result, box);
        return result;
    }
    
    private static void rangeQueryRicorsivo(Node node, List<GraphicObject> list, BoundingBox box) {
        if (node == null) {
            return;
        }
        for (Entry entry : node.entries()) {
            if (entry.getMBR().intersects(box)) {
                if (node.isLeaf())
                    list.add(entry.object());
                else
                    rangeQueryRicorsivo(entry.child(), list, box);
            }
        }
    }

    @Override
    public GraphicObject exactMatchQuery(GraphicObject obj) {
        Entry leaf = findLeaf(obj);
        return (leaf == null ? null : leaf.object());
    }

    @Override
    public void add(GraphicObject obj) {
        if (obj == null)
            return;
        insert(++count,obj);
    }
    
    public void insert(int id, GraphicObject obj){
        if (id == 0){
            id = ++count;
        }
        Entry e = new Entry(id, obj);
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
    public void delete(GraphicObject obj) {
        if (obj == null)
            return;
        Entry leafEntry = findLeaf(obj);
        if (leafEntry == null)
            return;
        Node n = leafEntry.belongingNode();
        n.deleteEntry(leafEntry);       
        List<Entry> toReInsert = condenseTree(n);
        for (Entry e : toReInsert){
            if (e.child() == null){
                insert(e.getId(), e.object());
            }
            else{
                List<Entry> leaves = new LinkedList<>();
                leaves(e.child(), leaves);
                for (Entry e1 : leaves){
                    insert(e1.getId(), e1.object());
                }
            }
        }
        if (root.size()==1){
            Node uniqueEntryChild = root.entries().get(0).child();
            if (uniqueEntryChild != null){
                uniqueEntryChild.setParentEntry(null);
                root = uniqueEntryChild;
            }
        }
    }

    public Entry findLeaf(GraphicObject obj){
        return findLeafRec(root, obj);
    }
    
    private static Entry findLeafRec(Node n, GraphicObject go){
        if (n.isLeaf()){
            for (Entry e : n.entries()){
                if (e.object().equals(go))
                    return e;
            }
            return null;
        }
        for (Entry e : n.entries()){
            if (e.getMBR().intersects(go.getBound())){
                Entry recursiveSearch = findLeafRec(e.child(), go);
                if (recursiveSearch == null)
                    continue;
                else
                    return recursiveSearch;
            }
        }
        return null;
    }
    
    private List<Entry> condenseTree(Node node){
        List<Entry> toReInsert = new LinkedList<>();
        while (node != root){
            Entry parentEntry = node.parentEntry();
            Node parentNode = node.parentNode();
            if (node.size() < m){
                parentNode.deleteEntry(parentEntry);
                parentEntry.setChild(null);
                toReInsert.addAll(node.entries());
            }
            node = node.parentNode();
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
    public int getId(GraphicObject obj){
        Entry e = findLeaf(obj);
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

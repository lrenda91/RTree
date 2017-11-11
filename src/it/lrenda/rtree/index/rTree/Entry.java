package it.lrenda.rtree.index.rTree;

import it.lrenda.rtree.objects.MBR.BoundingBox;
import it.lrenda.rtree.objects.MBR.BoundingBoxes;
import it.lrenda.rtree.objects.GraphicObject;

/**
 *
 * @author Luigi
 */
public class Entry {

    private GraphicObject object;
    private Node myNode,child;
    private BoundingBox MBR;
    private int id;

    /**
     * leaf entry
     */
    public Entry(int id, GraphicObject object) {
        this.child = null;
        this.id = id;
        this.object = object;
        this.MBR = object.getBound();
    }
    
    /**
     * non-leaf entry
     */
    public Entry(int id, Node child) {
        this.id = id;
        this.child = child;
        this.object = null;
        BoundingBox[] boxes = new BoundingBox[child.entries().size()];
        for (int i=0;i<boxes.length;i++){
            boxes[i] = child.entries().get(i).MBR;
        }
        this.MBR = BoundingBoxes.union(boxes);
        this.child.setParentEntry(this);
    }

    public GraphicObject object() {
        return object;
    }

    public Node child() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }
    
    public Node belongingNode(){
        return myNode;
    }

    public void setBelongingNode(Node node) {
         myNode = node;
    }

    public int getId() {
        return id;
    }

    public BoundingBox getMBR() {
        return MBR;
    }

    public void setMBR(BoundingBox MBR) {
        this.MBR = MBR;
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Entry)) return false;
        Entry other = (Entry) o;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "R"+id;
    }
    
    
    
}

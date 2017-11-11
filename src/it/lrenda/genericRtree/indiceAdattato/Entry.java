package it.lrenda.genericRtree.indiceAdattato;

import it.lrenda.genericRtree.MBR.BoundingBox2D;
import it.lrenda.genericRtree.MBR.BoundingBoxes2D;

/**
 *
 * @author Luigi
 */
public class Entry<K extends BoundingBox2D , V> {

    private V object;
    private Node myNode,child;
    private K MBR;
    private int id;

    /**
     * leaf entry
     */
    public Entry(int id, K MBR, V object) {
        this.child = null;
        this.id = id;
        this.object = object;
        this.MBR = MBR;
    }
    
    /**
     * non-leaf entry
     */
    public Entry(int id, Node child) {
        this.id = id;
        this.child = child;
        this.object = null;
        BoundingBox2D[] boxes = new BoundingBox2D[child.entries().size()];
        for (int i=0;i<boxes.length;i++){
            boxes[i] = child.entries().get(i).MBR;
        }
        this.MBR = (K) BoundingBoxes2D.union(boxes);
        this.child.setParentEntry(this);
    }

    public V object() {
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

    public BoundingBox2D getMBR() {
        return MBR;
    }

    public void setMBR(K MBR) {
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

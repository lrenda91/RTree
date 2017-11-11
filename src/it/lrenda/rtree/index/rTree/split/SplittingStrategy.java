package it.lrenda.rtree.index.rTree.split;

import it.lrenda.rtree.index.rTree.Entry;
import it.lrenda.rtree.index.rTree.Node;

/**
 *
 * @author Luigi
 */
public interface SplittingStrategy {
    
    Node split(Node fullLeaf, Entry entry, int minSize);
    
}

package it.lrenda.genericRtree.indiceAdattato.split;

import it.lrenda.genericRtree.indiceAdattato.Entry;
import it.lrenda.genericRtree.indiceAdattato.Node;

/**
 *
 * @author Luigi
 */
public interface SplittingStrategy {
    
    Node split(Node fullLeaf, Entry entry, int minSize);
    
}

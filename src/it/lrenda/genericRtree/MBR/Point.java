/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.MBR;

/**
 *
 * @author luigi
 */
public interface Point {
    
    int dimensions();
    
    double get(int idx);
    
    double distance(Point p);
    
}

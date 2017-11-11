/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lrenda.genericRtree.MBR;

/**
 *
 * @author luigi
 */
public abstract class AbstractPoint implements Point {
    
    private double[] coord;

    public AbstractPoint(double... coord) {
        this.coord = coord;
    }

    @Override
    public int dimensions() {
        return coord.length;
    }

    @Override
    public double get(int idx) {
        return coord[idx];
    }

    @Override
    public double distance(Point p) {
        if (!(p.dimensions() != this.dimensions())) 
            throw new IllegalArgumentException();
        double s = 0.0;
        for (int i=0;i<dimensions();i++){
            double d = Math.abs(this.get(i) - p.get(i));
            s += (Math.pow(d,2));
        }
        return Math.sqrt(s);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof AbstractPoint)) return false;
        AbstractPoint p = (AbstractPoint) obj;
        if (this.dimensions() != p.dimensions()) return false;
        for (int i=0;i<this.dimensions();i++){
            if (this.get(i) != p.get(i)) 
                return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i=0;i<dimensions();i++){
            sb.append(get(i));
            if (i<(dimensions()-1))
                sb.append(",");
        }
        return sb.append(")").toString();
    }
    
}

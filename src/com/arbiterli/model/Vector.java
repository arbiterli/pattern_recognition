package com.arbiterli.model;
/**
 * training vector including value.
 * @author ar
 *
 */
public class Vector {
    public int size;
    public double[] attr;
    public double value;
    
    public Vector(int size) {
        this.size = size;
        attr = new double[size];
    }
    
    /**
     * a format string seperated by ',' and end with value 
     */
    public Vector(String record) {
        String[] s = record.split(",");
        size = s.length - 1;
        attr = new double[size];
        for(int i=0;i<size;++i) {
            attr[i] = Double.parseDouble(s[i]);
        }
        value = Double.parseDouble(s[size]);
    }

    public double[] getAttr() {
        return attr;
    }

    public void setAttr(double[] attr) {
        this.attr = attr;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

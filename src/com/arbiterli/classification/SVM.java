package com.arbiterli.classification;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.arbiterli.linearRegression.Point;
import com.arbiterli.model.Matrix;
import com.arbiterli.model.Vector;

/**
 * support vector machine.
 */
public class SVM {
    private List<Vector> dataSet = new ArrayList<Vector>();
    //final weight parameter vector.
    private Matrix W;
    private double b;
    private double c;
    private int size;
    private double[] a;
    private int pointNum;
    private int[] y;
    private Matrix[] X;
    
    public SVM(int size) {
        this.size = size;
        W = new Matrix(1, size);
    }
    
    /**
     * init attributes after load data.
     */
    public void init(double c) {
    	this.c = c;
        pointNum = dataSet.size();
        a = new double[pointNum];
        X = new Matrix[pointNum];
        y = new int[pointNum];
        int index = 0;
        for(Vector v : dataSet) {
            Matrix m = new Matrix(1, size);
            for(int i=0;i<v.size;++i) {
            	m.setMatrixValue(v.getAttr()[i], 0, i);
            }
            X[index] = m;
            y[index] = (int)v.value;
            index++;
        }
        double sum = 0;
        for (int i=0;i<pointNum-1;++i) {
        	a[i] = 1;
        	sum += a[i]*y[i];
        }
        a[pointNum - 1] = -sum / y[pointNum - 1];
    }
    
    public void calculate() {
        double a1, a2; // represent new value for update.
        double bottom, top; // represent boundary for new a.
        double k; // k = k11 + k22 - 2k12.
        double e1, e2; // e1 = u1 - y1
        double updateScale = 100;
        double r; // old value of result.
        updateParameters();
        while(updateScale > 0.01) {
    	    System.out.println();
    	    System.out.println("W:" + W);
    	    System.out.println("b:" + b);
    	    r = calR();
    	    System.out.println("R:" + r);
    	    
    	    int first = chooseFirst();
    	    
    	    if(first == -1) {
    	        break;
    	    }
    	    int second = chooseSecond(first);
    	    System.out.println("first:" + first + "  second:" + second);
    	    k = calK(first,first) + calK(second,second) - 2*calK(first,second);
    	    System.out.println("k:" + k);
    	    
    	    e1 = calU(first) - y[first];
    	    e2 = calU(second) - y[second];
    	    a1 = a[first] + y[first]*(e2 - e1) / k;
    	    System.out.println("a1:" + a1);
    	    bottom = calBottom(first, second);
    	    top = calTop(first, second);
    	    if(a1 < bottom) {
    	        a1 = bottom;
    	    } else if(a1 > top) {
    	        a1 = top;
    	    }
    	    a2 = a[second] + y[first]*y[second]*(a[first] - a1);
    	    System.out.println("1old:" + a[first] + "  1new:" + a1);
    	    System.out.println("2old:" + a[second] + "  2new:" + a2);
    	    a[first] = a1;
    	    a[second] = a2;
    	    updateParameters();
    	    updateScale = Math.abs(calR() / r - 1);
    	    System.out.println("updateScale:" + updateScale);
    	}
    }
    
    private int chooseFirst() {
        for(int i=0;i<pointNum;++i) {
            if(!isFitKKT(i)) {
                return i;
            }
        }
        return -1;
    }
    
    private int chooseSecond(int index) {
        double e1 = y[index] - calU(index);
        double maxDeltaE = 0, e;
        int secondIndex = 0;
        for(int i=0;i<pointNum;++i) {
            e = y[i] - calU(i);
            if(Math.abs(e1 - e) > maxDeltaE) {
                maxDeltaE = Math.abs(e1 - e);
                secondIndex = i;
            }
        }
        return secondIndex;
    }
    
    private boolean isFitKKT(int index) {
        System.out.println("u:" + y[index]*calU(index));
        if(a[index] < 0.001) {
            return y[index]*calU(index) > 1.001;
        } else if (a[index] > c - 0.001) {
            return y[index]*calU(index) < 0.999;
        } else {
            return (y[index]*calU(index) < 1.001 && y[index]*calU(index) > 0.999);
        }
    }
    
    private double calBottom(int first, int second) {
        if(y[first]*y[second] == -1) {
            return Math.max(0, a[first] - a[second]);
        } else {
            return Math.max(0, a[first] + a[second] - c);
        }
    }
    
    private double calTop(int first, int second) {
        if(y[first]*y[second] == -1) {
            return Math.min(c, a[first] - a[second] + c);
        } else {
            return Math.min(c, a[first] + a[second]);
        }
    }
    
    private double calK(int i, int j) {
        return Matrix.multi(X[i], X[j].getTransfer()).getDeterminant();
    }
    
    private double calU(int index) {
    	return Matrix.multi(W, X[index].getTransfer()).getDeterminant() + b;
    }
    
    private double calR() {
        double sum1 = 0, sum2 = 0;
        for(int i=0;i<pointNum;++i) {
            sum1 += a[i];
            for(int j=0;j<pointNum;++j) {
                sum2 += y[i]*y[j]*a[i]*a[j]*calK(i,j);
            }
        }
        return sum1 - 0.5*sum2;
    }
    
    private void updateParameters() {
    	W = new Matrix(1, size);
    	double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
    	for(int i=0;i<pointNum;++i) {
    		W = Matrix.plus(W, Matrix.multi(X[i], a[i]*y[i]));
    	}
    	for(int i=0;i<pointNum;++i) {
            double WX = Matrix.multi(W, X[i].getTransfer()).getDeterminant();
            if(y[i] == -1 && WX > max) {
                max = WX;
            } else if(y[i] == 1 && WX < min) {
                min = WX;
            }
        }
    	b = -(max + min) / 2;
    }
    
    public void loadData(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        while((line = br.readLine()) != null) {
            dataSet.add(new Vector(line));
        }
        br.close();
        if(dataSet.isEmpty()) {
        	throw new Exception("no training data!");
        }
        if(dataSet.get(0).size != size) {
        	throw new Exception("size incorrect!");
        }
    }
    
    public List<Vector> getDataSet() {
        return dataSet;
    }
    
    public void addVector(Vector v) {
        dataSet.add(v);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

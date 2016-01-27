package com.arbiterli.classification;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.arbiterli.linearRegression.Point;
import com.arbiterli.model.Matrix;

public class Perceptron {
    private List<Point> dataSet = new ArrayList<Point>();
    //weight parameter vector.
    private Matrix W;
    private Matrix X;
    private int size; 
    
    public Perceptron(int size) {
        this.size = size;
        W = new Matrix(1, size + 1);
        X = new Matrix(size + 1, 1);
    }
    
    public void calculate() {
        for(int i=0;i<10;++i) {
            for(Point p : dataSet) {
                X.setMatrixValue(p.x, 0, 0);
                X.setMatrixValue(p.y, 1, 0);
                X.setMatrixValue(1, 2, 0);
                double y = Matrix.multi(W, X).getDeterminant();
                int a = (p.c == Color.red) ? 1 : -1;
                if(a*y < 10) {
                	X.setMatrixValue(y, 2, 0);
                	
                    W = Matrix.plus(W, Matrix.multi(X.getTransfer(), a));
                }
            }
        }
    }
    
    public void loadData(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        while((line = br.readLine()) != null) {
            String[] s = line.split(",");
            Color c = s[2].equals("1") ? Color.red : Color.blue;
            dataSet.add(new Point((int)Double.parseDouble(s[0]), (int)Double.parseDouble(s[1]), c));
        }
        br.close();
    }
    
    public Matrix getResult() {
        return W;
    }
    
    public List<Point> getDataSet() {
        return dataSet;
    }
    
    public void addPoint(Point p) {
        dataSet.add(p);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

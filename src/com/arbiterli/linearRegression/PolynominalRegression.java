package com.arbiterli.linearRegression;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.arbiterli.model.Matrix;

public class PolynominalRegression {
    private double lamda;
    private int order;
    private List<Point> dataSet = new ArrayList<Point>();
    // solve A*X = Y problem
    private Matrix A;
    private Matrix Y;
    private Matrix X;
    
    public PolynominalRegression(int order, double lamda) {
        this.order = order;
        this.lamda = lamda;
        A = new Matrix(order + 1, order + 1);
        Y = new Matrix(order + 1, 1);
    }
    
    public void calculate() {
        Matrix tmpY = new Matrix(order + 1, 1);
        Matrix tmpA = new Matrix(order + 1, order + 1);
        for(Point p : dataSet) {
            for(int i=0;i<order+1;++i) {
                tmpY.setMatrixValue(p.y*Math.pow(p.x, order-i), i, 0);
                for(int j=0;j<order+1;++j) {
                    tmpA.setMatrixValue(Math.pow(p.x, order+order-i-j), i, j);
                }
            }
            Y = Matrix.plus(Y, tmpY);
            A = Matrix.plus(A, tmpA);
        }
        Matrix lamdaMatrix = Matrix.multi(Matrix.getI(order + 1), lamda);
        lamdaMatrix.setMatrixValue(0, order, order);
        A = Matrix.plus(A, lamdaMatrix);
        X = Matrix.multi(A.getInverse(), Y);
    }
    
    public void loadData(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        while((line = br.readLine()) != null) {
            String[] s = line.split(",");
            addPoint(new Point((int)Double.parseDouble(s[0]), (int)Double.parseDouble(s[1]), Color.red));
        }
        br.close();
    }
    
    public Matrix getResult() {
        return X;
    }
    
    public List<Point> getDataSet() {
        return dataSet;
    }
    
    public void addPoint(Point p) {
        dataSet.add(p);
    }
}

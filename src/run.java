import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.arbiterli.classification.Perceptron;
import com.arbiterli.classification.SVM;
import com.arbiterli.data.DataGenerator;
import com.arbiterli.learning.ImageUtils;
import com.arbiterli.linearRegression.Paint;
import com.arbiterli.linearRegression.Point;
import com.arbiterli.linearRegression.PolynominalRegression;
import com.arbiterli.model.Matrix;



public class run {
    
    public static void main(String[] args) {
        
        try {
            //performPolynominalRegression(3);
            //performPerceptron();
        	//performSVM();
        	collectImage();
            System.out.println("done");
        } catch (Exception e) {
        	
            
            e.printStackTrace();
        }
    }
    
    private static void collectImage() throws Exception {
    	ImageUtils iu = new ImageUtils();
    	iu.collectImages(new File("C:/"), "S:/images");
    }
    
    public static void performSVM() throws Exception {
    	String path = "L:/classify.data";
        DataGenerator dg = new DataGenerator();
        dg.generateClassify(path);
        SVM svm = new SVM(2);
        svm.loadData(path);
        svm.init(1000);
        svm.calculate();
//        Paint p = Paint.getInstance();
//        p.addVector(svm.getDataSet());
//        p.showWindow();
    }

    public static void performPolynominalRegression(int order) throws Exception {
        String path = "S:/sin.data";
        DataGenerator dg = new DataGenerator();
        dg.generateSin(path);
        PolynominalRegression pr = new PolynominalRegression(order, 0);
        
        pr.loadData(path);
        
        pr.calculate();
        Matrix m = pr.getResult();
        System.out.println(m);
        
        Paint p = Paint.getInstance();
        p.addPoints(pr.getDataSet());
        for(int i=0;i<1000; ++i) {
            double x = i;
            double y = 0;
            for(int k=0;k<order+1;++k) {
                y += m.getMatrixValue(k, 0) * Math.pow(x, order-k);
            }
            Point po = new Point((int)(x+0.5),(int)(y+0.5),Color.green);
            p.addPoint(po);
            
        }
        
        p.showWindow();
    }
    
    public static void performPerceptron() throws Exception {
        String path = "F:/eclipse/files/classify.data";
        DataGenerator dg = new DataGenerator();
        dg.generateClassify(path);
        Perceptron per = new Perceptron(2);
        per.loadData(path);
        per.calculate();
        Matrix w = per.getResult();
        System.out.println(w);
        Paint p = Paint.getInstance();
        p.addPoints(per.getDataSet());
        //to calculate a and b in y = a*x + b
        double a = -w.getMatrixValue(0, 0) / w.getMatrixValue(0, 1);
        double b = -w.getMatrixValue(0, 2) / w.getMatrixValue(0, 1);
        System.out.println(a + "," + b);
        for(int i=0;i<1000; ++i) {
            double x = i;
            
            double y = a*x + b;
            
            Point po = new Point((int)(x+0.5),(int)(y+0.5),Color.green);
            p.addPoint(po);
            
        }
        p.showWindow();
    }
    
}

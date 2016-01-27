package com.arbiterli.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class DataGenerator {
	public void generateSin(String path) throws Exception {
		OutputStream os = new FileOutputStream(new File(path));
		for(int i=0;i<100;++i) {
			double x = (double)i/100;
			double y = Math.sin(2*Math.PI*x)* 100 + 200;
			os.write((String.valueOf(x*900) + "," + String.valueOf(y) + "\r\n").getBytes());
		}
		os.close();
	}
	
	public void generateClassify(String path) throws Exception {
	    OutputStream os = new FileOutputStream(new File(path));
	    Random r = new Random();
        for(int i=0;i<50;++i) {
            double x = 300 + 50*(r.nextDouble() - 0.5);
            double y = 100 + 50*(r.nextDouble() - 0.5);
            os.write((String.valueOf(x) + "," + String.valueOf(y) + ",1" + "\r\n").getBytes());
        }
        for(int i=0;i<50;++i) {
            double x = 100 + 50*(r.nextDouble() - 0.5);
            double y = 400 + 50*(r.nextDouble() - 0.5);
            os.write((String.valueOf(x) + "," + String.valueOf(y) + ",-1" + "\r\n").getBytes());
        }
        os.close();
	}
	
	public void generateCluster(String path) throws Exception {
	    OutputStream os = new FileOutputStream(new File(path));
        Random r = new Random();
        for(int i=0;i<50;++i) {
            double x = 300 + 50*(r.nextDouble() - 0.5);
            double y = 100 + 50*(r.nextDouble() - 0.5);
            os.write((String.valueOf(x) + "," + String.valueOf(y) + ",1" + "\r\n").getBytes());
        }
        for(int i=0;i<50;++i) {
            double x = 100 + 50*(r.nextDouble() - 0.5);
            double y = 400 + 50*(r.nextDouble() - 0.5);
            os.write((String.valueOf(x) + "," + String.valueOf(y) + ",2" + "\r\n").getBytes());
        }
        os.close();
	}
}

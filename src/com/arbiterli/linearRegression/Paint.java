package com.arbiterli.linearRegression;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.arbiterli.model.Vector;


public class Paint {
    private List<Point> points = new ArrayList<Point>();
    private static Paint INSTANCE = new Paint();
    
    public static Paint getInstance() {
        return INSTANCE;
    }
    
    public void showWindow() {
        Frame f = new Frame();
        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g){
                for(Point p : points) {
                	//System.out.println(p.x + "," + p.y);
                    g.setColor(p.c);
                    for(int i=-1;i<2;++i) {
                        for(int j=-1;j<2;++j) {
                            g.drawLine(p.x+i, p.y+j, p.x+i, p.y+j);
                        }
                    }
                }
            } 
        };
        f.add(canvas);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBackground(Color.lightGray);
        f.setLocationByPlatform(true);
        f.setSize(900,600);
        f.setVisible(true); 
    }
    
    public void addVector(List<Vector> vectors) {
        for(Vector v : vectors) {
            Color c = v.getValue() == 1 ? Color.red : Color.green;
            Point p = new Point((int)v.getAttr()[0], (int)v.getAttr()[1], c);
            points.add(p);
        }
    }
    
    public void addPoints(List<Point> points) {
        this.points.addAll(points);
    }
    
    public void addPoint(Point point) {
        this.points.add(point);
    }
    
    public void clearPoints() {
        points.clear();
    }
}


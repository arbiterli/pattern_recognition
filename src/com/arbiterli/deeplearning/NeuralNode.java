package com.arbiterli.deeplearning;

import java.util.Random;

import com.arbiterli.model.Matrix;

public class NeuralNode {
	private Matrix w; // 1*n matrix
	private double b;
	private double z; // z = w*x + b
	private double output;
	private double errorTerm;
	private int pos; // position in the layer.
	
	/**
	 * init a neural node, n is the number of nodes/elements which are connected to the node on the previous layer.
	 */
	public NeuralNode(int n, int pos) {
		w = Matrix.getRandom(1, n);
		b = new Random().nextDouble();
		this.pos = pos;
	}
	
	public double sigmoid(double a) {
		return 1 / (1 + Math.exp(a));
	}
	
	public double dSigmoid(double a) {
		return sigmoid(a) * (1 - sigmoid(a));
	}
	/**
	 * calculate the parameters and output.
	 * @param x x is input, a n*1 matrix
	 */
	public void calculateForward(Matrix x) {
	    z = Matrix.multi(w, x).getDeterminant() + b;
	    output = sigmoid(z);
	}
	
	public void calculateBackward(Matrix realResult, Layer nextLayer) {
	    if(nextLayer == null) {
	        errorTerm = (output - realResult.getMatrixValue(pos, 1)) * dSigmoid(z);
	    } else {
	        double error = 0;
	        for(NeuralNode node : nextLayer.getNodes()) {
	            error += node.getW().getMatrixValue(1, pos) * node.getErrorTerm();
	        }
	        errorTerm = error * dSigmoid(z);
	    }
	}

    public double getOutput() {
        return output;
    }

    public Matrix getW() {
        return w;
    }

    public double getErrorTerm() {
        return errorTerm;
    }
}

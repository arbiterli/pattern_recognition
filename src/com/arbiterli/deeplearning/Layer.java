package com.arbiterli.deeplearning;

import com.arbiterli.model.Matrix;

public class Layer {
    private NeuralNode[] nodes;
    private int size;
    private Matrix output;

    public Layer(int n, int lastN) {
        nodes = new NeuralNode[n];
        size = n;
        output = new Matrix(n, 1);
        for (int i = 0; i < n; ++i) {
            nodes[i] = new NeuralNode(lastN, i);
        }
    }

    /**
     * 
     * @param
     *            x is the input which is a n*1 matrix.
     */
    public void calculateForward(Matrix x) {
        for (int i = 0; i < size; ++i) {
            NeuralNode node = nodes[i];
            node.calculateForward(x);
            output.setMatrixValue(node.getOutput(), i, 1);
        }
    }

    public NeuralNode[] getNodes() {
        return nodes;
    }

    public void setNodes(NeuralNode[] nodes) {
        this.nodes = nodes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Matrix getOutput() {
        return output;
    }

    public void setOutput(Matrix output) {
        this.output = output;
    }
}

package com.arbiterli.deeplearning;

import java.util.ArrayList;
import java.util.List;

public class AutoEncoder {
    private List<Layer> layers = new ArrayList<Layer>();

    public AutoEncoder(int inputSize, int hiddenSize) {
        Layer hiddenLayer = new Layer(hiddenSize, inputSize);
        // auto encoder is encode input to itself, so the dimension is same.
        Layer outLayer = new Layer(inputSize, hiddenSize);
    }
    
    public void backPropagation() {
        
    }
}

package com.mir.newgift.model;

public class Feature {

    private int featureIndex;
    private int featureValue;

    public Feature(int featureIndex, int featureValue) {
        this.featureIndex = featureIndex;
        this.featureValue = featureValue;
    }

    public int getFeatureValue() {
        return featureValue;
    }
}

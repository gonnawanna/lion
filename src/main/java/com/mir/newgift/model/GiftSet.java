package com.mir.newgift.model;

import java.util.ArrayList;

public class GiftSet {

    private int id;
    private String firstProductName;
    private String secondProductName;
    private String thirdProductName;
    //may be class Product
    private float price;
    private ArrayList<Feature> features = new ArrayList<>();

    public GiftSet(String firstProductName, String secondProductName, String thirdProductName, float price) {
        this.firstProductName = firstProductName;
        this.secondProductName = secondProductName;
        this.thirdProductName = thirdProductName;
        this.price = price;
    }

    public String getFirstProductName() {
        return firstProductName;
    }

    public String getSecondProductName() {
        return secondProductName;
    }

    public String getThirdProductName() {
        return thirdProductName;
    }

    public float getPrice() {
        return price;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void addFeature (int featureIndex, int featureValue) {
        Feature feature = new Feature(featureIndex, featureValue);
        features.add(feature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftSet giftSet = (GiftSet) o;

        if (firstProductName != null ? !firstProductName.equals(giftSet.firstProductName) : giftSet.firstProductName != null)
            return false;
        if (secondProductName != null ? !secondProductName.equals(giftSet.secondProductName) : giftSet.secondProductName != null)
            return false;
        return thirdProductName != null ? thirdProductName.equals(giftSet.thirdProductName) : giftSet.thirdProductName == null;
    }

    @Override
    public int hashCode() {
        int result = firstProductName != null ? firstProductName.hashCode() : 0;
        result = 31 * result + (secondProductName != null ? secondProductName.hashCode() : 0);
        result = 31 * result + (thirdProductName != null ? thirdProductName.hashCode() : 0);
        return result;
    }
}

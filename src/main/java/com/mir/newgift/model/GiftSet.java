package com.mir.newgift.model;

import java.util.ArrayList;

public class GiftSet {

    private ArrayList<String> products;
    private float price;
    private ArrayList<String> features;
    private String firstProduct;
    private String secondProduct;
    private String thirdProduct;

    public void setProducts(ArrayList<String> products) {
        this.products = products;
        this.firstProduct = products.get(0);
        this.secondProduct = products.get(1);
        this.thirdProduct = products.get(2);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void addFeature (String feature) {
        features.add(feature);
    }

    public float getPrice() {
        return price;
    }

    public String getFirstProduct() {
        return firstProduct;
    }

    public String getSecondProduct() {
        return secondProduct;
    }

    public String getThirdProduct() {
        return thirdProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftSet giftSet = (GiftSet) o;

        return products != null ? products.containsAll(giftSet.products) : giftSet.products == null;
    }

    @Override
    public int hashCode() {
        return products != null ? products.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GiftSet{" +
                "firstProduct='" + firstProduct + '\'' +
                ", secondProduct='" + secondProduct + '\'' +
                ", thirdProduct='" + thirdProduct + '\'' +
                '}';
    }
}

package org.javiermf.features.models;


import java.util.HashSet;
import java.util.Set;

public class Product {

    String name;

    Set<Feature> productFeatures = new HashSet<Feature>();

    public Set<Feature> getProductFeatures() {
        return productFeatures;
    }

    public void addFeature(Feature feature) {
        productFeatures.add(feature);
    }

    public void removeFeature(Feature feature) {
        productFeatures.remove(feature);
    }

    public static Product buildWithFeatures(String... featureNames) {
        Product product = new Product();
        for (String featureName : featureNames) {
            product.addFeature(Feature.withName(product, featureName));
        }

        return product;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

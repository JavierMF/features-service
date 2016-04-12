package org.javiermf.features.models;

import java.util.HashSet;
import java.util.Set;


public class Configuration {

    String name;

    Product product;
    private Set<Feature> activedFeatures = new HashSet<Feature>();

    Set<Feature> getEnabledFeatures() {
        return new HashSet<Feature>();
    }

    public Set<String> availableFeatures() {
        return collectFeatureNames(product.getProductFeatures());
    }

    private Set<String> collectFeatureNames(Set<Feature> featuresSet) {
        Set<String> features = new HashSet<String>();
        for (Feature feature : featuresSet) {
            features.add(feature.name);
        }

        return features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<String> activedFeatures() {
        return collectFeatureNames(activedFeatures);
    }

    public void active(Feature feature) {
        activedFeatures.add(feature);

    }

    public void deactive(Feature feature) {
        activedFeatures.remove(feature);
    }
}

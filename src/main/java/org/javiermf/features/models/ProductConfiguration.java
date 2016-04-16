package org.javiermf.features.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductConfiguration {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @JsonIgnore
    @ManyToOne
    Product product;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Feature> activedFeatures = new HashSet<Feature>();

    public Set<Feature> getActivedFeatures() {
        return activedFeatures;
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

    public void active(String featureName) {
        for (Feature feature : product.getProductFeatures()) {
            if (featureName.equalsIgnoreCase(feature.getName())) {
                active(feature);
            }
        }

    }
}

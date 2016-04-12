package org.javiermf.features.models;


public class Feature {

    String name;
    String description;
    Product product;

    public static Feature withName(Product product, String featureName) {
        Feature feature = new Feature();
        feature.name = featureName;
        feature.product = product;
        return feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;

        Feature feature = (Feature) o;

        if (!name.equals(feature.name)) return false;
        return product.equals(feature.product);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}

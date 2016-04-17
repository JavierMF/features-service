package org.javiermf.features.services;

import org.javiermf.features.daos.ProductsConfigurationsDAO;
import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.exceptions.DuplicatedObjectException;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.Product;
import org.javiermf.features.models.ProductConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductsService {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProductsConfigurationsDAO productsConfigurationsDAO;


    public List<String> getAllProductNames() {
        List<String> allProducts = new ArrayList<String>();

        for (Product product : productsDAO.findAll()) {
            allProducts.add(product.getName());
        }

        return allProducts;
    }

    public Product findByName(String productName) {
        return productsDAO.findByName(productName);
    }

    public void deleteByName(String productName) {
        productsConfigurationsDAO.deleteConfigurationsForProduct(productName);
        productsDAO.deleteByName(productName);
    }

    public void add(String productName) {
        Product product = new Product();
        product.setName(productName);
        productsDAO.insert(product);
    }

    public Set<Feature> getFeaturesForProduct(String productName) {
        Product product = findByName(productName);
        return product.getProductFeatures();
    }

    public void addFeatureToProduct(String productName, String featureName, String featureDescription) {
        Product product = findByName(productName);
        if (product.hasFeatureNamed(featureName)) {
            throw new DuplicatedObjectException(featureName);
        }
        Feature feature = Feature.withName(product, featureName);
        feature.setDescription(featureDescription);
        productsDAO.insertFeature(feature);
    }

    @Transactional
    public Feature updateFeatureOfProduct(String productName, String featureName, String featureDescription) {
        Product product = productsDAO.findByName(productName);
        Feature feature = product.findProductFeatureByName(featureName);
        feature.setDescription(featureDescription);
        return feature;
    }

    @Transactional
    public void deleteFeatureOfProduct(String productName, String featureName) {
        Product product = productsDAO.findByName(productName);
        Feature feature = product.findProductFeatureByName(featureName);

        for (ProductConfiguration productConfiguration : productsConfigurationsDAO.findConfigurationsWithFeatureActive(feature)) {
            productConfiguration.getActivedFeatures().remove(feature);
        }

        productsDAO.deleteFeature(feature);
    }
}

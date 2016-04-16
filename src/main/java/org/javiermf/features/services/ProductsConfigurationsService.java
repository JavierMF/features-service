package org.javiermf.features.services;

import org.javiermf.features.daos.ProductsConfigurationsDAO;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.ProductConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsConfigurationsService {

    @Autowired
    ProductsConfigurationsDAO productsConfigurationsDAO;


    public List<String> getConfigurationsNamesForProduct(String productName) {
        List<String> configurationsForProduct = new ArrayList<String>();

        for (ProductConfiguration productConfiguration : productsConfigurationsDAO.findByProductName(productName)) {
            configurationsForProduct.add(productConfiguration.getName());
        }

        return configurationsForProduct;
    }

    public ProductConfiguration findByNameAndProductName(String productName, String configurationName) {
        return productsConfigurationsDAO.findByNameAndProductName(productName, configurationName);
    }

    public List<String> getConfigurationActivedFeaturesNames(String productName, String configurationName) {
        ProductConfiguration productConfiguration = productsConfigurationsDAO.findByNameAndProductName(productName, configurationName);
        List<String> featureNames = new ArrayList<String>();
        for (Feature feature : productConfiguration.getActivedFeatures()) {
            featureNames.add(feature.getName());
        }
        return featureNames;
    }

    public void deleteConfigurationsForProduct(String productName) {
        productsConfigurationsDAO.deleteConfigurationsForProduct(productName);
    }

    public List<ProductConfiguration> findConfigurationsWithFeatureActive(Feature feature) {
        return productsConfigurationsDAO.findConfigurationsWithFeatureActive(feature);
    }
}

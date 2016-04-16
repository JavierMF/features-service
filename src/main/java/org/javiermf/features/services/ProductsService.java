package org.javiermf.features.services;

import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductsService {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProductsConfigurationsService productsConfigurationsService;


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
        productsConfigurationsService.deleteConfigurationsForProduct(productName);
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
}

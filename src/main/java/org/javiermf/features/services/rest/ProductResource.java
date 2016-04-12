package org.javiermf.features.services.rest;


import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.models.Configuration;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    ProductsDAO productsDAO;

    @RequestMapping(method = GET)
    public List<String> getAllProducts() {
        List<String> allProducts = new ArrayList<String>();

        for (Product product : productsDAO.getAllProducts()) {
            allProducts.add(product.getName());
        }

        return allProducts;
    }

    @RequestMapping(value = "/{productName}", method = RequestMethod.GET)
    public Product getProductByName(@PathVariable String productName) {
        return productsDAO.getProductByName(productName);
    }

    @RequestMapping(value = "/{productName}/configurations", method = RequestMethod.GET)
    public List<String> getConfigurationsForProduct(@PathVariable String productName) {
        List<String> configurationsForProduct = new ArrayList<String>();

        for (Configuration configuration : productsDAO.getConfigurationsForProduct(productName)) {
            configurationsForProduct.add(configuration.getName());
        }

        return configurationsForProduct;
    }

    @RequestMapping(value = "/{productName}/configurations/{configurationName}", method = RequestMethod.GET)
    public Configuration getConfigurationWithNameForProduct(@PathVariable String productName, @PathVariable String configurationName) {
        return productsDAO.getConfigurationWithNameForProduct(productName, configurationName);
    }

    @RequestMapping(value = "/{productName}/configurations/{configurationName}/features", method = RequestMethod.GET)
    public List<String> getConfigurationActivedFeatures(@PathVariable String productName, @PathVariable String configurationName) {
        Configuration configuration = productsDAO.getConfigurationWithNameForProduct(productName, configurationName);
        List<String> featureNames = new ArrayList<String>();
        for (Feature feature : configuration.getActivedFeatures()) {
            featureNames.add(feature.getName());
        }
        return featureNames;
    }




    /*
    /products/
    /products/NAME  GET, POST, DELETE
    /products/NAME/features/  GET
    /products/NAME/features/NAME  POST,PUT, DELETE
    /products/NAME/configurations/ GET
    /products/NAME/configurations/NAME/ GET, POST, DELETE
    /products/NAME/configurations/NAME/features/NAME POST, DELETE
     */
}

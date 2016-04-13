package org.javiermf.features.services.rest;


import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.models.Configuration;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;


@Component
@Path("/products")
@Produces("application/json")
public class ProductResource {

    @Autowired
    ProductsDAO productsDAO;

    @GET
    public List<String> getAllProducts() {
        List<String> allProducts = new ArrayList<String>();

        for (Product product : productsDAO.getAllProducts()) {
            allProducts.add(product.getName());
        }

        return allProducts;
    }

    @Path("{productName}")
    @GET
    public Product getProductByName(@PathParam("productName") String productName) {
        return productsDAO.getProductByName(productName);
    }

    @Path("{productName}/configurations")
    @GET
    public List<String> getConfigurationsForProduct(@PathParam("productName") String productName) {
        List<String> configurationsForProduct = new ArrayList<String>();

        for (Configuration configuration : productsDAO.getConfigurationsForProduct(productName)) {
            configurationsForProduct.add(configuration.getName());
        }

        return configurationsForProduct;
    }

    @Path("{productName}/configurations/{configurationName}")
    @GET
    public Configuration getConfigurationWithNameForProduct(@PathParam("productName") String productName,
                                                            @PathParam("configurationName") String configurationName) {
        return productsDAO.getConfigurationWithNameForProduct(productName, configurationName);
    }

    @Path("{productName}/configurations/{configurationName}/features")
    @GET
    public List<String> getConfigurationActivedFeatures(@PathParam("productName") String productName,
                                                        @PathParam("configurationName") String configurationName) {
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

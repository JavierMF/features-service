package org.javiermf.features.services.rest;


import org.javiermf.features.daos.ProductsConfigurationsDAO;
import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.models.Feature;
import org.javiermf.features.models.Product;
import org.javiermf.features.models.ProductConfiguration;
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

    @Autowired
    ProductsConfigurationsDAO configsDAO;


    @GET
    public List<String> getAllProducts() {
        List<String> allProducts = new ArrayList<String>();

        for (Product product : productsDAO.findAll()) {
            allProducts.add(product.getName());
        }

        return allProducts;
    }

    @Path("{productName}")
    @GET
    public Product getProductByName(@PathParam("productName") String productName) {
        return productsDAO.findByName(productName);
    }

    @Path("{productName}/configurations")
    @GET
    public List<String> getConfigurationsForProduct(@PathParam("productName") String productName) {
        List<String> configurationsForProduct = new ArrayList<String>();

        for (ProductConfiguration productConfiguration : configsDAO.findByProductName(productName)) {
            configurationsForProduct.add(productConfiguration.getName());
        }

        return configurationsForProduct;
    }

    @Path("{productName}/configurations/{configurationName}")
    @GET
    public ProductConfiguration getConfigurationWithNameForProduct(@PathParam("productName") String productName,
                                                                   @PathParam("configurationName") String configurationName) {
        return configsDAO.findByNameAndProductName(productName, configurationName);
    }

    @Path("{productName}/configurations/{configurationName}/features")
    @GET
    public List<String> getConfigurationActivedFeatures(@PathParam("productName") String productName,
                                                        @PathParam("configurationName") String configurationName) {
        ProductConfiguration productConfiguration = configsDAO.findByNameAndProductName(productName, configurationName);
        List<String> featureNames = new ArrayList<String>();
        for (Feature feature : productConfiguration.getActivedFeatures()) {
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

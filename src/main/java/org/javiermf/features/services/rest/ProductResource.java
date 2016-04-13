package org.javiermf.features.services.rest;


import org.javiermf.features.models.Product;
import org.javiermf.features.models.ProductConfiguration;
import org.javiermf.features.services.ProductsConfigurationsService;
import org.javiermf.features.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


@Component
@Path("/products")
@Produces("application/json")
public class ProductResource {

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsConfigurationsService configurationsService;

    @GET
    public List<String> getAllProducts() {
        return productsService.getAllProductNames();
    }

    @Path("{productName}")
    @GET
    public Product getProductByName(@PathParam("productName") String productName) {
        return productsService.findByName(productName);
    }

    @Path("{productName}/configurations")
    @GET
    public List<String> getConfigurationsForProduct(@PathParam("productName") String productName) {
        return configurationsService.getConfigurationsNamesForProduct(productName);

    }

    @Path("{productName}/configurations/{configurationName}")
    @GET
    public ProductConfiguration getConfigurationWithNameForProduct(@PathParam("productName") String productName,
                                                                   @PathParam("configurationName") String configurationName) {
        return configurationsService.findByNameAndProductName(productName, configurationName);
    }

    @Path("{productName}/configurations/{configurationName}/features")
    @GET
    public List<String> getConfigurationActivedFeatures(@PathParam("productName") String productName,
                                                        @PathParam("configurationName") String configurationName) {
        return configurationsService.getConfigurationActivedFeaturesNames(productName, configurationName);

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

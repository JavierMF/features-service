package org.javiermf.features.services.rest;


import org.javiermf.features.models.Product;
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
public class ProductsResource {

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsConfigurationResource productsConfigurationResource;

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
    public ProductsConfigurationResource productsConfigurationResource() {
        return productsConfigurationResource;
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

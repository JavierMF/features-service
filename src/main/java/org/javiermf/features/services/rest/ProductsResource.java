package org.javiermf.features.services.rest;


import org.javiermf.features.models.Product;
import org.javiermf.features.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@Component
@Path("/products")
@Produces("application/json")
public class ProductsResource {

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsConfigurationResource productsConfigurationResource;

    @Autowired
    ProductsFeaturesResource productsFeaturesResource;


    @GET
    public List<String> getAllProducts() {
        return productsService.getAllProductNames();
    }

    @Path("{productName}")
    @GET
    public Product getProductByName(@PathParam("productName") String productName) {
        return productsService.findByName(productName);
    }

    @Path("{productName}")
    @DELETE
    public Response deleteProductByName(@PathParam("productName") String productName) {
        productsService.deleteByName(productName);
        return Response.noContent().build();
    }

    @Path("{productName}")
    @POST
    public Response addProduct(@PathParam("productName") String productName) throws URISyntaxException {
        productsService.add(productName);
        return Response.created(new URI("/products/" + productName)).build();
    }


    @Path("{productName}/configurations")
    public ProductsConfigurationResource productsConfigurationResource() {
        return productsConfigurationResource;
    }

    @Path("{productName}/features")
    public ProductsFeaturesResource productsFeaturesResource() {
        return productsFeaturesResource;
    }



    /*
    OK /products/
    OK /products/NAME  GET
    OK /products/NAME  POST
    OK /products/NAME  DELETE
    OK /products/NAME/features/  GET
       /products/NAME/features/NAME  POST
       /products/NAME/features/NAME  PUT
       /products/NAME/features/NAME  DELETE

    OK /products/NAME/configurations/ GET
    OK /products/NAME/configurations/NAME GET
       /products/NAME/configurations/NAME POST
       /products/NAME/configurations/NAME UPDATE
       /products/NAME/configurations/NAME DELETE
       /products/NAME/configurations/NAME/features/NAME POST
       /products/NAME/configurations/NAME/features/NAME DELETE
     */
}

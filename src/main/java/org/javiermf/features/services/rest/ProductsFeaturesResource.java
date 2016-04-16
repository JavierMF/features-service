package org.javiermf.features.services.rest;

import org.javiermf.features.models.Feature;
import org.javiermf.features.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Set;

@Component
@Produces("application/json")
public class ProductsFeaturesResource {

    @Autowired
    ProductsService productsService;

    @GET
    public Set<Feature> getFeaturesForProduct(@PathParam("productName") String productName) {
        return productsService.getFeaturesForProduct(productName);

    }
}

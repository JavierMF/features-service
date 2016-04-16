package org.javiermf.features.services.rest;

import org.javiermf.features.models.ProductConfiguration;
import org.javiermf.features.services.ProductsConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
@Produces("application/json")
public class ProductsConfigurationResource {

    @Autowired
    ProductsConfigurationsService configurationsService;

    @GET
    public List<String> getConfigurationsForProduct(@PathParam("productName") String productName) {
        return configurationsService.getConfigurationsNamesForProduct(productName);

    }

    @Path("/{configurationName}")
    @GET
    public ProductConfiguration getConfigurationWithNameForProduct(@PathParam("productName") String productName,
                                                                   @PathParam("configurationName") String configurationName) {
        return configurationsService.findByNameAndProductName(productName, configurationName);
    }

    @Path("/{configurationName}/features")
    @GET
    public List<String> getConfigurationActivedFeatures(@PathParam("productName") String productName,
                                                        @PathParam("configurationName") String configurationName) {
        return configurationsService.getConfigurationActivedFeaturesNames(productName, configurationName);

    }

    @POST
    @Path("/{configurationName}")
    public Response addConfiguration(@PathParam("productName") String productName,
                                     @PathParam("configurationName") String configurationName) throws URISyntaxException {
        configurationsService.add(productName, configurationName);
        return Response.created(new URI("/products/" + productName + "/configurations/" + configurationName)).build();
    }

}
